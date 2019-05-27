import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProjectResourceAssignService } from '../project-resource-assign/project-resource-assign.service';
import * as $ from 'jquery';
import 'popper.js';
import 'bootstrap';

@Component({
    selector: 'jhi-resource-report-by-client',
    templateUrl: './resource-report-by-client.component.html'
})
export class ResourceReportByClientComponent implements OnInit {
    projectsInPrms: any[];
    clientsInHRM: any[];
    resourcesOnClients: any[];
    resourcesInProject: any[];
    resourcesOnAvailability: any[];
    constructor(protected projectResourceAssignService: ProjectResourceAssignService, protected activatedRoute: ActivatedRoute) {}
    ngOnInit() {
        this.loadProjectsOnPrms();
        this.getClientsFromSunHrm();
    }

    previousState() {
        window.history.back();
    }

    getClientsFromSunHrm() {
        this.projectResourceAssignService.getClientsFromSunHrm().subscribe(result => {
            this.clientsInHRM = result.body;
        });
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

    getAvailableResource(selectedRange) {
        this.projectResourceAssignService.geResourcesOfSelectedProject(selectedRange).subscribe(result => {
            this.resourcesInProject = result.body;
        });
    }
}
