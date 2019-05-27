import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { IProjectResourceAssign } from 'app/shared/model/project-resource-assign.model';
import { ProjectResourceAssignService } from './project-resource-assign.service';
import { FormGroup, FormBuilder, Validators, FormControl, FormArray } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { NgbDateStruct, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';

export class ProjectResourceAssignDTO {
    id?: number;
    projectId?: number;
    fromDate?: any;
    toDate?: any;
    billingType?: number;
    location?: number;
    estimatedStaffHours?: number;
    actualStaffHours?: number;
    // multipleResources?: [];
}

@Component({
    selector: 'jhi-project-resource-assign-update',
    templateUrl: './project-resource-assign-update.component.html'
})
export class ProjectResourceAssignUpdateComponent implements OnInit {
    projectResourceAssign: IProjectResourceAssign;
    isSaving: boolean;
    fromDateDp: any;
    toDateDp: any;
    createdAtDp: any;
    updatedAtDp: any;
    rolesArray: any[];
    projectsArray: any[];
    resourcesBasedOnRole: any[];
    selectedMultipleDropdown: any[];
    projectResourceAssignForm: FormGroup;
    demo: any[] = [];
    projectDetail: any;
    showAvailablePercentage: Boolean[] = [];
    occupiedPercentage: any[] = [];
    testDate: NgbDateStruct = null;
    onUpdateDisabled: boolean = false;

    constructor(
        protected projectResourceAssignService: ProjectResourceAssignService,
        protected activatedRoute: ActivatedRoute,
        private formBuilder: FormBuilder,
        private datePipe: DatePipe,
        private ngbDateParserFormatter: NgbDateParserFormatter
    ) {}

    ngOnInit() {
        this.showAvailablePercentage[0] = true;
        this.occupiedPercentage[0] = 0;
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ projectResourceAssign }) => {
            this.projectResourceAssign = projectResourceAssign;
            console.log(this.projectResourceAssign);
        });
        // this.loadRolesForDropDown();
        this.loadProjectsDropDown();
        this.loadSubunitsInHrm();
        this.projectResourceAssignForm = this.formBuilder.group({
            id: new FormControl(),
            projectId: new FormControl('', Validators.required),
            fromDate: new FormControl('', Validators.required),
            toDate: new FormControl('', Validators.required),
            billingType: new FormControl('', Validators.required),
            location: new FormControl('', Validators.required),
            estimatedStaffHours: new FormControl('', Validators.required),
            // actualStaffHours: new FormControl('', Validators.required),
            multipleResources: this.formBuilder.array([this.initiateMultipleResources()])
        });
        if (this.projectResourceAssign.id !== undefined) {
            // make the fields disabled on updating
            this.onUpdateDisabled = true;
            this.projectResourceAssignForm.controls.projectId.disable();
            this.loadResourceBasedOnRole(this.projectResourceAssign.role, 0);
            // On updating the project resource to load the values.
            this.projectResourceAssignForm.patchValue({
                id: this.projectResourceAssign.id,
                projectId: this.projectResourceAssign.projectId,
                fromDate: this.projectResourceAssign.fromDate,
                toDate: this.projectResourceAssign.toDate,
                billingType: this.projectResourceAssign.billingType,
                location: this.projectResourceAssign.location,
                estimatedStaffHours: this.projectResourceAssign.estimatedStaffHours
                // actualStaffHours: this.projectResourceAssign.actualStaffHours
            });
            const multipleResourceArray = <FormArray>this.projectResourceAssignForm.get('multipleResources');
            console.log("Length of the array "+multipleResourceArray.length);
            for (let i = 0; i < multipleResourceArray.length ; i++) {
                const multiResourceControl = <FormArray>this.projectResourceAssignForm.get('multipleResources');
                const gettingField: FormGroup = <FormGroup>multiResourceControl.controls[i];
                gettingField.controls.empId.disable();
                gettingField.controls.role.disable();
            }
            

            multipleResourceArray.patchValue([
                {
                    role: this.projectResourceAssign.role,
                    empId: this.projectResourceAssign.empId,
                    billValue: this.projectResourceAssign.billValue,
                    percentageOfWork: this.projectResourceAssign.percentageOfWork
                }
            ]);
        }
    }

    /**
     * To initiate set of arrays.
     */
    initiateMultipleResources() {
        return this.formBuilder.group({
            role: new FormControl('', Validators.required),
            empId: new FormControl('', Validators.required),
            billValue: new FormControl('', Validators.required),
            percentageOfWork: new FormControl('', Validators.required)
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        const formToSend = Object.assign(new ProjectResourceAssignDTO(), this.projectResourceAssignForm.value);
        console.log(formToSend);
        this.isSaving = true;
        if (this.projectResourceAssign.id !== undefined) {
            this.subscribeToSaveResponse(this.projectResourceAssignService.update(formToSend));
        } else {
            this.subscribeToSaveResponse(this.projectResourceAssignService.create(formToSend));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectResourceAssign>>) {
        result.subscribe(
            (res: HttpResponse<IProjectResourceAssign>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    /**
     * To load the sub units in HRM system.
     */
    loadSubunitsInHrm() {
        this.projectResourceAssignService.getSubunitsInHrm().subscribe((result: any) => {
            this.rolesArray = result.body;
        });
    }

    /**
     * To load all the roles available in the PRMS System.
     */
    loadRolesForDropDown() {
        // Calling a service file.
        this.projectResourceAssignService.loadRolesInPrms().subscribe((result: any) => {
            this.rolesArray = result.body;
        });
    }

    loadProjectsDropDown() {
        this.projectResourceAssignService.loadProjectsInPrms().subscribe((result: any) => {
            this.projectsArray = result.body;
        });
    }

    loadStartAndEndDate(projectId) {
        this.projectResourceAssignService.getStartDateAndEndDate(projectId).subscribe((result: any) => {
            this.projectDetail = result.body;
            console.log(this.projectDetail);
            let startDate = moment(this.projectDetail.startDate);
            let endDate = moment(this.projectDetail.endDate);
            console.log('Project start and end date');
            this.projectResourceAssignForm.patchValue({
                fromDate: startDate,
                toDate: endDate
            });
        });
    }

    /**
     * Called when a role is selected.
     * @param selectedRole The whole role-dropdown element
     */
    loadResourceBasedOnRole(selectedRole, index) {
        // this.projectResourceAssignService.loadResourceBasedOnRole(selectedRole).subscribe(
        //     (result: any) => {
        //         this.resourcesBasedOnRole = result.body;
        //         this.demo.splice(index, 0, this.resourcesBasedOnRole);
        //     }
        // );

        this.projectResourceAssignService.getEmployeesFromSelectedDepartment(selectedRole).subscribe((result: any) => {
            this.resourcesBasedOnRole = result.body;
            this.demo.splice(index, 0, this.resourcesBasedOnRole);
        });
    }

    /**
     * Add one more row of fields to enable multiple resources assignment.
     */
    addOneMoreResource(index) {
        console.log(index);
        this.occupiedPercentage[index + 1] = 0;
        this.showAvailablePercentage[index + 1] = true;
        const control = <FormArray>this.projectResourceAssignForm.get('multipleResources');
        if (control.valid) {
            control.push(this.initiateMultipleResources());
        }
    }

    /**
     * To remove the selected row from the array.
     */
    removeResource(index: number) {
        const control = <FormArray>this.projectResourceAssignForm.get('multipleResources');
        control.removeAt(index);
    }

    /**
     * To validate whether the resource is selected already in the same role.
     * @param selectedResource Resource selected in the dropdown box.
     * @param index The row number which the user had seleced the name.
     */
    validateForResourceField(selectedResource: number, index: number) {
        const selectedRows = this.projectResourceAssignForm.value.multipleResources;
        const selectedRole = this.projectResourceAssignForm.value.multipleResources[index].role;
        // To find the other rows where the selected resource is selected.
        const selectedResourceInOtherRows = [];
        for (let i = 0; i < selectedRows.length; i++) {
            if (i !== index) {
                if (selectedRows[i].empId === selectedResource) {
                    selectedResourceInOtherRows.push(selectedRows[i]);
                }
            }
        }
        // To check the other rows whether the same role is selected.
        for (let j = 0; j < selectedResourceInOtherRows.length; j++) {
            if (selectedResourceInOtherRows[j].role === selectedRole) {
                const multiResourceControl = <FormArray>this.projectResourceAssignForm.get('multipleResources');
                const gettingField: FormGroup = <FormGroup>multiResourceControl.controls[index];
                gettingField.controls.empId.setErrors({ resourceOnSameRole: true });
            } else {
                const multiResourceControl = <FormArray>this.projectResourceAssignForm.get('multipleResources');
                const gettingField: FormGroup = <FormGroup>multiResourceControl.controls[index];
                gettingField.controls.empId.setErrors({ resourceOnSameRole: false });
            }
        }
    }

    /**
     * To check if the resource is already assigned for the selected role for the selected project.
     */
    checkResourceAlreadyInSelectedProject(selectedResource: number, index: number) {
        const selectedProject = this.projectResourceAssignForm.getRawValue().projectId;
        // const multipleFormArray = this.projectResourceAssignForm.get('multipleResources');
        const form = this.projectResourceAssignForm.value;
        const selectedRole = form.multipleResources[index].role;
        // const selectedRole = this.projectResourceAssignForm.getRawValue().role;
        if (selectedProject !== undefined && selectedProject !== '' && selectedRole !== undefined && selectedRole !== '') {
            this.projectResourceAssignService
                .checkResourceAlreadyInSelectedProject(selectedResource, selectedProject, selectedRole)
                .subscribe(result => {
                    if (result.body === true) {
                        const multiResourceControl = <FormArray>this.projectResourceAssignForm.get('multipleResources');
                        const gettingField: FormGroup = <FormGroup>multiResourceControl.controls[index];
                        gettingField.controls.empId.setErrors({ resourceAlreadyAllocated: true });
                    }
                });
        } else {
            const multiResourceControl = <FormArray>this.projectResourceAssignForm.get('multipleResources');
            const gettingField: FormGroup = <FormGroup>multiResourceControl.controls[index];
            gettingField.controls.empId.setErrors({ projectNotSelected: true });
        }

        this.validatePercentageAllocation(0, index);
    }

    /**
     * To check the resource is having entered percentage value.
     * @param enteredPerncentage Percentage value entered by the user
     * @param index The row which the user is entering the value
     */
    validatePercentageAllocation(enteredPerncentage: number, index: number) {
        const wholeForm = this.projectResourceAssignForm.value;
        const selectedResource = wholeForm.multipleResources[index].empId;
        // To make the field valid or not.
        console.log(enteredPerncentage);
        console.log(selectedResource);
        this.projectResourceAssignService.validateAvailalbePercentage(enteredPerncentage, selectedResource).subscribe((result: any) => {
            console.log(result.body);
            this.showAvailablePercentage[index] = false;
            this.occupiedPercentage[index] = result.body;
        });
    }

    getAddresses(form) {
        return form.get('multipleResources').controls;
    }

    get formData() {
        return this.projectResourceAssignForm.get('multipleResources');
    }

    getField(index?: number) {
        const multiResourceControl = <FormArray>this.projectResourceAssignForm.get('multipleResources');
        const gettingField: FormGroup = <FormGroup>multiResourceControl.controls[index];
        return gettingField;
    }

    preventNegativeInput(event) {
        if (!((event.keyCode > 95 && event.keyCode < 106) || (event.keyCode > 47 && event.keyCode < 58) || event.keyCode === 8)) {
            return false;
        }
    }
    // get formData() {
    //     return <FormArray>this.projectResourceAssignForm.get('multipleResources');
    // }
}
