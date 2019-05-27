import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EmployeeSalary } from 'app/shared/model/employee-salary.model';
import { EmployeeSalaryService } from './employee-salary.service';
import { EmployeeSalaryComponent } from './employee-salary.component';
import { EmployeeSalaryDetailComponent } from './employee-salary-detail.component';
import { EmployeeSalaryUpdateComponent } from './employee-salary-update.component';
import { EmployeeSalaryDeletePopupComponent } from './employee-salary-delete-dialog.component';
import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';

@Injectable({ providedIn: 'root' })
export class EmployeeSalaryResolve implements Resolve<IEmployeeSalary> {
    constructor(private service: EmployeeSalaryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<EmployeeSalary> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EmployeeSalary>) => response.ok),
                map((employeeSalary: HttpResponse<EmployeeSalary>) => employeeSalary.body)
            );
        }
        return of(new EmployeeSalary());
    }
}

export const employeeSalaryRoute: Routes = [
    {
        path: 'employee-salary',
        component: EmployeeSalaryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_HR_MANAGER', 'ROLE_FINANCIAL_ANALYST'],
            defaultSort: 'id,asc',
            pageTitle: 'EmployeeSalaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee-salary/:id/view',
        component: EmployeeSalaryDetailComponent,
        resolve: {
            employeeSalary: EmployeeSalaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeSalaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee-salary/new',
        component: EmployeeSalaryUpdateComponent,
        resolve: {
            employeeSalary: EmployeeSalaryResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_HR_MANAGER', 'ROLE_FINANCIAL_ANALYST'],
            pageTitle: 'EmployeeSalaries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'employee-salary/:id/edit',
        component: EmployeeSalaryUpdateComponent,
        resolve: {
            employeeSalary: EmployeeSalaryResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_HR_MANAGER', 'ROLE_FINANCIAL_ANALYST'],
            pageTitle: 'EmployeeSalaries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeeSalaryPopupRoute: Routes = [
    {
        path: 'employee-salary/:id/delete',
        component: EmployeeSalaryDeletePopupComponent,
        resolve: {
            employeeSalary: EmployeeSalaryResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_HR_MANAGER', 'ROLE_FINANCIAL_ANALYST'],
            pageTitle: 'EmployeeSalaries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
