import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';
import { EmployeeSalaryService } from './employee-salary.service';

@Component({
    selector: 'jhi-employee-salary-update',
    templateUrl: './employee-salary-update.component.html'
})
export class EmployeeSalaryUpdateComponent implements OnInit {
    employeeSalary: IEmployeeSalary;
    isSaving: boolean;
    createdAtDp: any;
    updatedAtDp: any;
    subUnitsArray: any[];
    employeesInDepartment: any[];

    constructor(protected employeeSalaryService: EmployeeSalaryService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ employeeSalary }) => {
            this.employeeSalary = employeeSalary;
        });
        this.loadSubunitsInHrm();
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.employeeSalary.id !== undefined) {
            this.subscribeToSaveResponse(this.employeeSalaryService.update(this.employeeSalary));
        } else {
            this.subscribeToSaveResponse(this.employeeSalaryService.create(this.employeeSalary));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeSalary>>) {
        result.subscribe((res: HttpResponse<IEmployeeSalary>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
        this.employeeSalaryService.getSubunitsInHrm().subscribe((result: any) => {
            this.subUnitsArray = result.body;
        });
    }

    loadEmployeeFromDepartment(selectedDepartment: number) {
        this.employeeSalaryService.getEmployeesFromSelectedDepartment(selectedDepartment).subscribe((result: any) => {
            this.employeesInDepartment = result.body;
        });
    }

    preventNegativeInput(event) {
        if (!((event.keyCode > 95 && event.keyCode < 106) || (event.keyCode > 47 && event.keyCode < 58) || event.keyCode === 8)) {
            // if (!(event.keyCode === 189 || event.keyCode === 109)) {
            return false;
            // console.log(event, event.keyCode, event.keyIdentifier);
        }
    }
}
