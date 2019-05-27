import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IBilling } from 'app/shared/model/billing.model';
import { BillingService } from './billing.service';

@Component({
    selector: 'jhi-billing-update',
    templateUrl: './billing-update.component.html'
})
export class BillingUpdateComponent implements OnInit {
    billing: IBilling;
    isSaving: boolean;
    createdAtDp: any;
    updatedAtDp: any;
    hrmProjectsArray: any[];
    resourcesArray: any[];
    hrmClientsArray: any[];

    constructor(protected billingService: BillingService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ billing }) => {
            this.billing = billing;
            if (this.billing.id !== undefined) {
                this.loadResourcesOnProject(this.billing.project);
                this.loadProjectsOfSelectedClient(this.billing.clientId);
            }
        });
        // Initializing the forms.
        // this.loadProjectsDropDown();
        this.loadClientsInSunHrm();
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.billing.id !== undefined) {
            this.subscribeToSaveResponse(this.billingService.update(this.billing));
        } else {
            this.subscribeToSaveResponse(this.billingService.create(this.billing));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBilling>>) {
        result.subscribe((res: HttpResponse<IBilling>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    /**
     * To get the Clients that are created in the SUNHRM.
     */
    loadClientsInSunHrm() {
        this.billingService.getClientsFromSunHrm().subscribe((result: any) => {
            this.hrmClientsArray = result.body;
        });
    }

    /**
     * When the project is selected, to load the resources
     * @param selectedProjectDropdown
     */
    loadResourcesOnProject(selectedProject) {
        this.billingService.geResourcesOfSelectedProject(selectedProject).subscribe(
            (result: any) => {
                this.resourcesArray = result.body;
            },
            err => {
                console.log('Error has occured');
            }
        );
    }

    loadSalaryAndHoursWorked(selectedResourceDropdown) {
        const selectedResource = selectedResourceDropdown.target.value;
        const selectedProject = this.billing.project;
        this.billingService.getHoursOfResource(selectedProject, selectedResource).subscribe(
            (result: any) => {
                this.billing.hoursAllocated = result.body;
            },
            err => {
                console.log('Error has occured');
            }
        );
        // to get the salary of the selected resource
        this.billingService.getSalaryOfResource(selectedResource).subscribe((result: any) => {
            if (result.body.salary !== undefined && result.body.salary !== '') {
                this.billing.salary = result.body.salary;
            }
        });
        const selectedRecourceObject = this.resourcesArray.find(o => o.empId === selectedResource);

        this.getBillValueOfResource(selectedProject, selectedResource);
        this.billingService.getBillValueOfResource(selectedProject, selectedResource).subscribe(result => {});
    }

    /**
     * To load the projects of the selected client from the dropdown
     */
    loadProjectsOfSelectedClient(selectedClient) {
        this.billingService.getProjectsOfSelectedClient(selectedClient).subscribe((result: any) => {
            this.hrmProjectsArray = result.body;
        });
    }

    /**
     * To load the billed value of the selected project
     */
    getBillValueOfResource(project, resource) {
        this.billingService.getBillValueOfResource(project, resource).subscribe(result => {
            this.billing.billRate = result.body;
        });
    }

    preventNegativeInput(event) {
        if (!((event.keyCode > 95 && event.keyCode < 106) || (event.keyCode > 47 && event.keyCode < 58) || event.keyCode === 8)) {
            return false;
        }
    }
}
