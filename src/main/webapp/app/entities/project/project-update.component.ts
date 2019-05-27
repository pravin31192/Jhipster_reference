import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from './project.service';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';

const clients = [
    'Thrivent Financials for Lutheran',
    'FHLBNY',
    'FHLBATL',
    'Sun Technologies',
    'Halco',
    'Havertys',
    'Macys'
];

@Component({
    selector: 'jhi-project-update',
    templateUrl: './project-update.component.html'
})
export class ProjectUpdateComponent implements OnInit {
    project: IProject;
    isSaving: boolean;
    startDateDp: any;
    endDateDp: any;
    createdAtDp: any;
    updatedAtDp: any;
    public model: any;
    hrmProjectsArray: any[];
    hrmClientsArray: any[];
    projectsOfClientArray: any[];

    constructor(protected projectService: ProjectService, protected activatedRoute: ActivatedRoute) {}

    search = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            distinctUntilChanged(),
            map(term => (term.length < 2 ? [] : clients.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)))
    );

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ project }) => {
            this.project = project;
        });
        console.log('Updating client name');
        if (this.project.id !== undefined) {
            this.loadProjectsOfSelectedClientOnEdit(this.project.hrmClientId);
        }
        // this.loadProjectsInSunHrm();
        this.loadClientsInSunHrm();
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.project.id !== undefined) {
            this.subscribeToSaveResponse(this.projectService.update(this.project));
        } else {
            this.subscribeToSaveResponse(this.projectService.create(this.project));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProject>>) {
        result.subscribe((res: HttpResponse<IProject>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    /**
     * To get the projects that are created in the SUNHRM.
     */
    /* loadProjectsInSunHrm() {
        this.projectService.getProjectsFromSunHrm().subscribe(
            (result: any) => {
                this.hrmProjectsArray = result.body;
            }
        );
    } */

    /**
     * To get the Clients that are created in the SUNHRM.
     */
    loadClientsInSunHrm() {
        this.projectService.getClientsFromSunHrm().subscribe(
            (result: any) => {
                this.hrmClientsArray = result.body;
            }
        );
    }

    /**
     *  To generate a project code based on the Client code and next id.
     */
    loadProjectCode(selectedClientCode) {
        console.log(selectedClientCode.target.value);
        this.project.projectCode = selectedClientCode.target.value;
    }

    /**
     * To load the projects of the selected client from the dropdown
     */
    loadProjectsOfSelectedClient(selectedClientDropdown) {
        const selectedClient = selectedClientDropdown.target.value;
        this.projectService.getProjectsOfSelectedClient(selectedClient).subscribe(
            (result: any) => {
                this.hrmProjectsArray = result.body;
            }
        );
    }

    /**
     * To load the projects of the selected client from the dropdown
     */
    loadProjectsOfSelectedClientOnEdit(selectedClient: number) {
        this.projectService.getProjectsOfSelectedClient(selectedClient).subscribe(
            (result: any) => {
                this.hrmProjectsArray = result.body;
            }
        );
    }
}
