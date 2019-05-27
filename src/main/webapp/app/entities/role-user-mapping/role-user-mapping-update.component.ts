import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IRoleUserMapping } from 'app/shared/model/role-user-mapping.model';
import { RoleUserMappingService } from './role-user-mapping.service';
import { EmployeeSalaryService } from './../employee-salary/employee-salary.service';

@Component({
    selector: 'jhi-role-user-mapping-update',
    templateUrl: './role-user-mapping-update.component.html'
})
export class RoleUserMappingUpdateComponent implements OnInit {
    roleUserMapping: IRoleUserMapping;
    isSaving: boolean;
    createdAtDp: any;
    updatedAtDp: any;
    subUnitsArray: any[];
    employeesInDepartment: any[];
    rolesArray: any[];

    constructor(
        protected roleUserMappingService: RoleUserMappingService,
        protected activatedRoute: ActivatedRoute,
        protected employeeSalaryService: EmployeeSalaryService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ roleUserMapping }) => {
            this.roleUserMapping = roleUserMapping;
        });
        this.loadSubunitsInHrm();
        this.loadRolesInPrms();
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.roleUserMapping.id !== undefined) {
            this.subscribeToSaveResponse(this.roleUserMappingService.update(this.roleUserMapping));
        } else {
            this.subscribeToSaveResponse(this.roleUserMappingService.create(this.roleUserMapping));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoleUserMapping>>) {
        result.subscribe((res: HttpResponse<IRoleUserMapping>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
        this.employeeSalaryService.getSubunitsInHrm().subscribe(
            (result: any) => {
                this.subUnitsArray = result.body;
            }
        );
    }

    loadEmployeeFromDepartment(selectedDepartment: number) {
        this.employeeSalaryService.getEmployeesFromSelectedDepartment(selectedDepartment).subscribe(
            (result: any) => {
                this.employeesInDepartment = result.body;
            }
        );
    }

    loadRolesInPrms() {
        this.roleUserMappingService.loadRolesInPrms().subscribe(
            (result: any) => {
                this.rolesArray = result.body;
            }
        );
    }
}
