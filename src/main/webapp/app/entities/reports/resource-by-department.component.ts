import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProjectResourceAssignService } from '../project-resource-assign/project-resource-assign.service';
import { FormGroup, FormBuilder, Validators, FormControl, FormArray } from '@angular/forms';
import * as $ from 'jquery';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { IRoleUserMapping } from 'app/shared/model/role-user-mapping.model';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import 'popper.js';
import 'bootstrap';
import { RoleUserMappingService } from '../role-user-mapping/role-user-mapping.service';
import { EmployeeSalaryService } from './../employee-salary/employee-salary.service';
import { IProjectResourceAssign } from 'app/shared/model/project-resource-assign.model';

// import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
// import { DatePipe } from '@angular/common';

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
    selector: 'jhi-resource-by-department.component',
    templateUrl: './resource-by-department.component.html'
})
export class ResourceByDepartmentComponent implements OnInit {
    projectsInPrms: any[];
    clientsInHRM: any[];
    resourcesOnAvailability: any[];
    resourcesOnClients: any[];
    resourcesBasedOnRole: any[];
    demo: any[] = [];
    demoEng: any[] = [];
    rolesArray: any[];
    supportArray: any[];
    engineeringArray: any[];
    projectResourceAssign: IProjectResourceAssign;
    isSaving: boolean;
    fromDateDp: any;
    toDateDp: any;
    createdAtDp: any;
    updatedAtDp: any;
    employeesInDepartment: any[];
    // demoLength: any = 12;
    //  projectsArray: any[];
    selectedMultipleDropdown: any[];
    projectResourceAssignForm: FormGroup;
    projectDetail: any;
    showAvailablePercentage: Boolean[] = [];
    occupiedPercentage: any[] = [];
    //    testDate: NgbDateStruct = null;

    constructor(
        protected projectResourceAssignService: ProjectResourceAssignService,
        private formBuilder: FormBuilder,
        protected activatedRoute: ActivatedRoute,
        protected employeeSalaryService: EmployeeSalaryService
    ) {}
    ngOnInit() {
        // this.demoLength = 12;
        this.loadProjectsOnPrms();
        this.getClientsFromSunHrm();

        this.showAvailablePercentage[0] = true;
        this.occupiedPercentage[0] = 0;
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ projectResourceAssign }) => {
            this.projectResourceAssign = projectResourceAssign;
            console.log(this.projectResourceAssign);
        });
        // this.loadRolesForDropDown();
        // this.loadProjectsDropDown();
        this.loadEngineeringUnitsInHrm();
        this.loadSupportUnitsInHrm();
        this.projectResourceAssignForm = this.formBuilder.group({
            id: new FormControl(),
            projectId: new FormControl('', Validators.required),
            fromDate: new FormControl('', Validators.required),
            toDate: new FormControl('', Validators.required),
            billingType: new FormControl('', Validators.required),
            location: new FormControl('', Validators.required),
            estimatedStaffHours: new FormControl('', Validators.required),
            actualStaffHours: new FormControl('', Validators.required),
            multipleResources: this.formBuilder.array([this.initiateMultipleResources()])
        });
        if (this.projectResourceAssign.id !== undefined) {
            this.loadResourceBasedOnRole(this.projectResourceAssign.role, 0);
            this.loadResourceBasedOnRoleEng(this.projectResourceAssign.role, 0);
            // On updating the project resource to load the values.
            this.projectResourceAssignForm.patchValue({
                id: this.projectResourceAssign.id,
                projectId: this.projectResourceAssign.projectId,
                fromDate: this.projectResourceAssign.fromDate,
                toDate: this.projectResourceAssign.toDate,
                billingType: this.projectResourceAssign.billingType,
                location: this.projectResourceAssign.location,
                estimatedStaffHours: this.projectResourceAssign.estimatedStaffHours,
                actualStaffHours: this.projectResourceAssign.actualStaffHours
            });
            const multipleResourceArray = <FormArray>this.projectResourceAssignForm.get('multipleResources');
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

    getResourcesOnClient(selectedClient) {
        this.projectResourceAssignService.getResourcesOfClients(selectedClient).subscribe(
            result => {
                this.resourcesOnClients = result.body;
            },
            error => {
                this.resourcesOnClients = [];
            }
        );
    }

    loadProjectsOnPrms() {
        this.projectResourceAssignService.loadProjectsInPrms().subscribe(result => {
            this.projectsInPrms = result.body;
        });
    }

    getClientsFromSunHrm() {
        this.projectResourceAssignService.getClientsFromSunHrm().subscribe(result => {
            this.clientsInHRM = result.body;
        });
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

    /**
     * To load the sub units in HRM system.
     */

    loadSupportUnitsInHrm() {
        this.projectResourceAssignService.getSubunitsInHrm().subscribe((result: any) => {
            // this.supportArray = result.body;
            this.supportArray = result.body.filter(function(el) {
                return (
                    el['id'] === 8 ||
                    el.id === 18 ||
                    el.id === 11 ||
                    el.id === 12 ||
                    el.id === 10 ||
                    el.id === 26 ||
                    el.id === 17 ||
                    el.id === 19 ||
                    el.id === 21 ||
                    el.id === 4 ||
                    el.id === 5 ||
                    el.id === 20 ||
                    el.id === 2 ||
                    el.id === 1
                );
            });

            console.log('This is Support BODY at id=8: ', result.body);
        });
    }

    loadEngineeringUnitsInHrm() {
        this.projectResourceAssignService.getSubunitsInHrm().subscribe((result: any) => {
            console.log('This is Engineering BODY ', result.body[0]);
            // this.engineeringArray = result.body;
            this.engineeringArray = result.body.filter(function(el) {
                return el['id'] === 3 || el.id === 9 || el.id === 34 || el.id === 36 || el.id === 6 || el.id === 13;
            });
        });
    }

    loadEmployeeFromDepartment(selectedDepartment: number) {
        this.employeeSalaryService.getEmployeesFromSelectedDepartment(selectedDepartment).subscribe((result: any) => {
            this.employeesInDepartment = result.body;
        });
    }

    /**
     * To load all the roles available in the PRMS System.
     */
    loadRolesForDropDown() {
        // Calling a service file.
        this.projectResourceAssignService.loadRolesInPrms().subscribe((result: any) => {
            this.rolesArray = result.body;
            console.log('This is RESULT BODY at id=8: ', result.body[0].name);
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

    loadResourceBasedOnRole(selectedRole, index) {
        this.projectResourceAssignService.getEmployeesFromSelectedDepartment(selectedRole).subscribe((result: any) => {
            this.resourcesBasedOnRole = result.body;
            this.demo.splice(index, 0, this.resourcesBasedOnRole);
            // this.demoLength = this.demo.length;
        });
    }

    loadResourceBasedOnRoleEng(selectedRole, index) {
        this.projectResourceAssignService.getEmployeesFromSelectedDepartment(selectedRole).subscribe((result: any) => {
            this.resourcesBasedOnRole = result.body;

            this.demoEng.splice(index, 0, this.resourcesBasedOnRole);
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
}
