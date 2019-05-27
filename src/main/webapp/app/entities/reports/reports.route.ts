import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';

import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';

import { ProjectResourceAssign } from 'app/shared/model/project-resource-assign.model';
// import { ProjectResourceAssignReportComponent } from './project-resource-assign-report.component';
import { ResourceByDepartmentComponent } from './resource-by-department.component';
import { ResourceReportByClientComponent } from './resource-report-by-client.component';
import { ResourceReportByProjectComponent } from './resource-report-by-project.component';
import { ResourceReportByPercentageComponent } from './resource-report-by-percentage.component';
import { IProjectResourceAssign } from 'app/shared/model/project-resource-assign.model';
import { ProjectResourceAssignService } from '../project-resource-assign/project-resource-assign.service';
import { SunTechResourceStrengthComponent } from 'app/entities/reports/suntech-resource-strength.component';

@Injectable({ providedIn: 'root' })
export class ProjectResourceAssignResolve implements Resolve<IProjectResourceAssign> {
    constructor(private service: ProjectResourceAssignService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ProjectResourceAssign> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProjectResourceAssign>) => response.ok),
                map((projectResourceAssign: HttpResponse<ProjectResourceAssign>) => projectResourceAssign.body)
            );
        }
        return of(new ProjectResourceAssign());
    }
}

// {
//     path: 'resource-report-by-client',
//     component: ResourceReportByClientComponent,
//     resolve: {
//         pagingParams: JhiResolvePagingParams
//     },
//     data: {
//         authorities: ['ROLE_USER'],
//         defaultSort: 'id,asc',
//         pageTitle: 'ProjectResourceAssigns'
//     },
//     canActivate: [UserRouteAccessService]
// },
// {
//     path: 'project-resource-assign/:id/view',
//     component: ProjectResourceAssignDetailComponent,
//     resolve: {
//         projectResourceAssign: ProjectResourceAssignResolve
//     },
//     data: {
//         authorities: ['ROLE_USER'],
//         pageTitle: 'ProjectResourceAssigns'
//     },
//     canActivate: [UserRouteAccessService]
// },
// {
//     path: 'project-resource-assign/new',
//     component: ProjectResourceAssignUpdateComponent,
//     resolve: {
//         projectResourceAssign: ProjectResourceAssignResolve
//     },
//     data: {
//         authorities: ['ROLE_USER'],
//         pageTitle: 'ProjectResourceAssigns'
//     },
//     canActivate: [UserRouteAccessService]
// },
// {
//     path: 'project-resource-assign/:id/edit',
//     component: ProjectResourceAssignUpdateComponent,
//     resolve: {
//         projectResourceAssign: ProjectResourceAssignResolve
//     },
//     data: {
//         authorities: ['ROLE_USER'],
//         pageTitle: 'ProjectResourceAssigns'
//     },
//     canActivate: [UserRouteAccessService]
// },

export const reportsRoute: Routes = [
    {
        path: 'resource-report-by-client',
        component: ResourceReportByClientComponent,
        // resolve: {
        //     projectResourceAssign: ProjectResourceAssignResolve
        // },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_SALES_MANAGER', 'ROLE_HR_MANAGER', 'ROLE_FINANCIAL_ANALYST', 'ROLE_PROJECT_MANAGER'],
            pageTitle: 'Resource Report by Client'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'resource-report-by-project',
        component: ResourceReportByProjectComponent,
        // resolve: {
        //     projectResourceAssign: ProjectResourceAssignResolve
        // },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_SALES_MANAGER', 'ROLE_HR_MANAGER', 'ROLE_FINANCIAL_ANALYST', 'ROLE_PROJECT_MANAGER'],
            pageTitle: 'Resource Report by Project'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'resource-report-by-percentage',
        component: ResourceReportByPercentageComponent,
        // resolve: {
        //     projectResourceAssign: ProjectResourceAssignResolve
        // },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_SALES_MANAGER', 'ROLE_HR_MANAGER', 'ROLE_FINANCIAL_ANALYST', 'ROLE_PROJECT_MANAGER'],
            pageTitle: 'Resource Report by percentage'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'resource-by-department',
        component: ResourceByDepartmentComponent,
        // resolve: {
        //     projectResourceAssign: ProjectResourceAssignResolve
        // },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_SALES_MANAGER', 'ROLE_HR_MANAGER', 'ROLE_FINANCIAL_ANALYST'],
            pageTitle: 'Resource Department Assign'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'suntech-resource-strength',
        component: SunTechResourceStrengthComponent,
        // resolve: {
        //     projectResourceAssign: ProjectResourceAssignResolve
        // },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_SALES_MANAGER', 'ROLE_HR_MANAGER', 'ROLE_FINANCIAL_ANALYST'],
            pageTitle: 'Resource Department Assign'
        },
        canActivate: [UserRouteAccessService]
    }
];
