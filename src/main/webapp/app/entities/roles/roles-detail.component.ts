import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRoles } from 'app/shared/model/roles.model';

@Component({
    selector: 'jhi-roles-detail',
    templateUrl: './roles-detail.component.html'
})
export class RolesDetailComponent implements OnInit {
    roles: IRoles;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ roles }) => {
            this.roles = roles;
        });
    }

    previousState() {
        window.history.back();
    }
}
