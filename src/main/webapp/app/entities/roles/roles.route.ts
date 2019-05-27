import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Roles } from 'app/shared/model/roles.model';
import { RolesService } from './roles.service';
import { RolesComponent } from './roles.component';
import { RolesDetailComponent } from './roles-detail.component';
import { RolesUpdateComponent } from './roles-update.component';
import { RolesDeletePopupComponent } from './roles-delete-dialog.component';
import { IRoles } from 'app/shared/model/roles.model';

@Injectable({ providedIn: 'root' })
export class RolesResolve implements Resolve<IRoles> {
    constructor(private service: RolesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Roles> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Roles>) => response.ok),
                map((roles: HttpResponse<Roles>) => roles.body)
            );
        }
        return of(new Roles());
    }
}

export const rolesRoute: Routes = [
    {
        path: 'roles',
        component: RolesComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Roles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'roles/:id/view',
        component: RolesDetailComponent,
        resolve: {
            roles: RolesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Roles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'roles/new',
        component: RolesUpdateComponent,
        resolve: {
            roles: RolesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Roles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'roles/:id/edit',
        component: RolesUpdateComponent,
        resolve: {
            roles: RolesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Roles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rolesPopupRoute: Routes = [
    {
        path: 'roles/:id/delete',
        component: RolesDeletePopupComponent,
        resolve: {
            roles: RolesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Roles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
