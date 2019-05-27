import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RoleUserMapping } from 'app/shared/model/role-user-mapping.model';
import { RoleUserMappingService } from './role-user-mapping.service';
import { RoleUserMappingComponent } from './role-user-mapping.component';
import { RoleUserMappingDetailComponent } from './role-user-mapping-detail.component';
import { RoleUserMappingUpdateComponent } from './role-user-mapping-update.component';
import { RoleUserMappingDeletePopupComponent } from './role-user-mapping-delete-dialog.component';
import { IRoleUserMapping } from 'app/shared/model/role-user-mapping.model';

@Injectable({ providedIn: 'root' })
export class RoleUserMappingResolve implements Resolve<IRoleUserMapping> {
    constructor(private service: RoleUserMappingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RoleUserMapping> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RoleUserMapping>) => response.ok),
                map((roleUserMapping: HttpResponse<RoleUserMapping>) => roleUserMapping.body)
            );
        }
        return of(new RoleUserMapping());
    }
}

export const roleUserMappingRoute: Routes = [
    {
        path: 'role-user-mapping',
        component: RoleUserMappingComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'RoleUserMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'role-user-mapping/:id/view',
        component: RoleUserMappingDetailComponent,
        resolve: {
            roleUserMapping: RoleUserMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RoleUserMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'role-user-mapping/new',
        component: RoleUserMappingUpdateComponent,
        resolve: {
            roleUserMapping: RoleUserMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RoleUserMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'role-user-mapping/:id/edit',
        component: RoleUserMappingUpdateComponent,
        resolve: {
            roleUserMapping: RoleUserMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RoleUserMappings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const roleUserMappingPopupRoute: Routes = [
    {
        path: 'role-user-mapping/:id/delete',
        component: RoleUserMappingDeletePopupComponent,
        resolve: {
            roleUserMapping: RoleUserMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RoleUserMappings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
