import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OhrmCustomer } from 'app/shared/model/ohrm-customer.model';
import { OhrmCustomerService } from './ohrm-customer.service';
import { OhrmCustomerComponent } from './ohrm-customer.component';
import { OhrmCustomerDetailComponent } from './ohrm-customer-detail.component';
import { OhrmCustomerUpdateComponent } from './ohrm-customer-update.component';
import { OhrmCustomerDeletePopupComponent } from './ohrm-customer-delete-dialog.component';
import { IOhrmCustomer } from 'app/shared/model/ohrm-customer.model';

@Injectable({ providedIn: 'root' })
export class OhrmCustomerResolve implements Resolve<IOhrmCustomer> {
    constructor(private service: OhrmCustomerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<OhrmCustomer> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<OhrmCustomer>) => response.ok),
                map((ohrmCustomer: HttpResponse<OhrmCustomer>) => ohrmCustomer.body)
            );
        }
        return of(new OhrmCustomer());
    }
}

export const ohrmCustomerRoute: Routes = [
    {
        path: 'ohrm-customer',
        component: OhrmCustomerComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'OhrmCustomers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ohrm-customer/:id/view',
        component: OhrmCustomerDetailComponent,
        resolve: {
            ohrmCustomer: OhrmCustomerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OhrmCustomers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ohrm-customer/new',
        component: OhrmCustomerUpdateComponent,
        resolve: {
            ohrmCustomer: OhrmCustomerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OhrmCustomers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ohrm-customer/:id/edit',
        component: OhrmCustomerUpdateComponent,
        resolve: {
            ohrmCustomer: OhrmCustomerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OhrmCustomers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ohrmCustomerPopupRoute: Routes = [
    {
        path: 'ohrm-customer/:id/delete',
        component: OhrmCustomerDeletePopupComponent,
        resolve: {
            ohrmCustomer: OhrmCustomerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OhrmCustomers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
