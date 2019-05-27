import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Billing } from 'app/shared/model/billing.model';
import { BillingService } from './billing.service';
import { BillingComponent } from './billing.component';
import { BillingDetailComponent } from './billing-detail.component';
import { BillingUpdateComponent } from './billing-update.component';
import { BillingDeletePopupComponent } from './billing-delete-dialog.component';
import { IBilling } from 'app/shared/model/billing.model';
import { BillingReportComponent } from './billing-report.component';

@Injectable({ providedIn: 'root' })
export class BillingResolve implements Resolve<IBilling> {
    constructor(private service: BillingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Billing> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Billing>) => response.ok),
                map((billing: HttpResponse<Billing>) => billing.body)
            );
        }
        return of(new Billing());
    }
}

export const billingRoute: Routes = [
    {
        path: 'billing',
        component: BillingComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_SALES_MANAGER', 'ROLE_FINANCIAL_ANALYST', 'ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'Billings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'billing/:id/view',
        component: BillingDetailComponent,
        resolve: {
            billing: BillingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Billings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'billing/new',
        component: BillingUpdateComponent,
        resolve: {
            billing: BillingResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_SALES_MANAGER', 'ROLE_FINANCIAL_ANALYST', 'ROLE_ADMIN'],
            pageTitle: 'Billings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'billing/:id/edit',
        component: BillingUpdateComponent,
        resolve: {
            billing: BillingResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_SALES_MANAGER', 'ROLE_FINANCIAL_ANALYST', 'ROLE_ADMIN'],
            pageTitle: 'Billings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'billing/report',
        component: BillingReportComponent,
        resolve: {
            billing: BillingResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'Billing Report'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const billingPopupRoute: Routes = [
    {
        path: 'billing/:id/delete',
        component: BillingDeletePopupComponent,
        resolve: {
            billing: BillingResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_SALES_MANAGER', 'ROLE_FINANCIAL_ANALYST', 'ROLE_ADMIN'],
            pageTitle: 'Billings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
