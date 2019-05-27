import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectResourceAssign } from 'app/shared/model/project-resource-assign.model';

@Component({
    selector: 'jhi-project-resource-assign-detail',
    templateUrl: './project-resource-assign-detail.component.html'
})
export class ProjectResourceAssignDetailComponent implements OnInit {
    projectResourceAssign: IProjectResourceAssign;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ projectResourceAssign }) => {
            this.projectResourceAssign = projectResourceAssign;
        });
    }

    previousState() {
        window.history.back();
    }
}
