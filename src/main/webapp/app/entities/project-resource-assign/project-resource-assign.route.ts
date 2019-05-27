import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProjectResourceAssign } from 'app/shared/model/project-resource-assign.model';
import { ProjectResourceAssignService } from './project-resource-assign.service';
import { ProjectResourceAssignComponent } from './project-resource-assign.component';
import { ProjectResourceAssignDetailComponent } from './project-resource-assign-detail.component';
import { ProjectResourceAssignUpdateComponent } from './project-resource-assign-update.component';
import { ProjectResourceAssignDeletePopupComponent } from './project-resource-assign-delete-dialog.component';
import { IProjectResourceAssign } from 'app/shared/model/project-resource-assign.model';
import { ProjectResourceAssignReportComponent } from './project-resource-assign-report.component';

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

export const projectResourceAssignRoute: Routes = [
    {
        path: 'project-resource-assign',
        component: ProjectResourceAssignComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_PROJECT_MANAGER', 'ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'ProjectResourceAssigns'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'project-resource-assign/:id/view',
        component: ProjectResourceAssignDetailComponent,
        resolve: {
            projectResourceAssign: ProjectResourceAssignResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProjectResourceAssigns'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'project-resource-assign/new',
        component: ProjectResourceAssignUpdateComponent,
        resolve: {
            projectResourceAssign: ProjectResourceAssignResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_PROJECT_MANAGER', 'ROLE_ADMIN'],
            pageTitle: 'ProjectResourceAssigns'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'project-resource-assign/:id/edit',
        component: ProjectResourceAssignUpdateComponent,
        resolve: {
            projectResourceAssign: ProjectResourceAssignResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_PROJECT_MANAGER', 'ROLE_ADMIN'],
            pageTitle: 'ProjectResourceAssigns'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'project-resource-assign/report',
        component: ProjectResourceAssignReportComponent,
        resolve: {
            projectResourceAssign: ProjectResourceAssignResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_PROJECT_MANAGER'],
            pageTitle: 'Resource Reports'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const projectResourceAssignPopupRoute: Routes = [
    {
        path: 'project-resource-assign/:id/delete',
        component: ProjectResourceAssignDeletePopupComponent,
        resolve: {
            projectResourceAssign: ProjectResourceAssignResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_PROJECT_MANAGER'],
            pageTitle: 'ProjectResourceAssigns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
