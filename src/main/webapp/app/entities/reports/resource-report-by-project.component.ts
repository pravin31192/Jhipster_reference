import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProjectResourceAssignService } from '../project-resource-assign/project-resource-assign.service';
import * as $ from 'jquery';
import 'popper.js';
import 'bootstrap';

@Component({
    selector: 'jhi-resource-report-by-project',
    templateUrl: './resource-report-by-project.component.html'
})
export class ResourceReportByProjectComponent implements OnInit {
    projectsInPrms: any[];
    clientsInHRM: any[];
    resourcesInProject: any[];
    constructor(protected projectResourceAssignService: ProjectResourceAssignService, protected activatedRoute: ActivatedRoute) {}
    ngOnInit() {
        this.loadProjectsOnPrms();
    }

    previousState() {
        window.history.back();
    }

    loadProjectsOnPrms() {
        this.projectResourceAssignService.loadProjectsInPrms().subscribe(result => {
            this.projectsInPrms = result.body;
        });
    }

    getResourcesInProject(selectedProject) {
        this.projectResourceAssignService.geResourcesOfSelectedProject(selectedProject).subscribe(result => {
            this.resourcesInProject = result.body;
        });
    }

    getAvailableResource(selectedRange) {
        this.projectResourceAssignService.geResourcesOfSelectedProject(selectedRange).subscribe(result => {
            this.resourcesInProject = result.body;
        });
    }
}
