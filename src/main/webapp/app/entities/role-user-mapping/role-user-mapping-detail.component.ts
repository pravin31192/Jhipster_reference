import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRoleUserMapping } from 'app/shared/model/role-user-mapping.model';

@Component({
    selector: 'jhi-role-user-mapping-detail',
    templateUrl: './role-user-mapping-detail.component.html'
})
export class RoleUserMappingDetailComponent implements OnInit {
    roleUserMapping: IRoleUserMapping;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ roleUserMapping }) => {
            this.roleUserMapping = roleUserMapping;
        });
    }

    previousState() {
        window.history.back();
    }
}
