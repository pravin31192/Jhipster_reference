import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IRoles } from 'app/shared/model/roles.model';
import { RolesService } from './roles.service';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';

@Component({
    selector: 'jhi-roles-update',
    templateUrl: './roles-update.component.html'
})
export class RolesUpdateComponent implements OnInit {
    roles: IRoles;
    isSaving: boolean;
    createdAtDp: any;
    updatedAtDp: any;
    RolesForm: FormGroup;

    constructor(protected rolesService: RolesService, private formBuilder: FormBuilder, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ roles }) => {
            this.roles = roles;
        });

        // this.RolesForm = this.formBuilder.group({
        //     // id: new FormControl(),
        //     name: new FormControl('', Validators.required),
        //     description: new FormControl('', Validators.required)
        // });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.roles.id !== undefined) {
            this.subscribeToSaveResponse(this.rolesService.update(this.roles));
        } else {
            this.subscribeToSaveResponse(this.rolesService.create(this.roles));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoles>>) {
        result.subscribe((res: HttpResponse<IRoles>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
