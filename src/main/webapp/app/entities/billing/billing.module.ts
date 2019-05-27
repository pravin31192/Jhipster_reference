import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrmsSharedModule } from 'app/shared';
import {
    BillingComponent,
    BillingDetailComponent,
    BillingUpdateComponent,
    BillingDeletePopupComponent,
    BillingDeleteDialogComponent,
    billingRoute,
    billingPopupRoute
} from './';
import { BillingReportComponent } from './billing-report.component';

const ENTITY_STATES = [...billingRoute, ...billingPopupRoute];

@NgModule({
    imports: [PrmsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BillingComponent,
        BillingDetailComponent,
        BillingUpdateComponent,
        BillingDeleteDialogComponent,
        BillingDeletePopupComponent,
        BillingReportComponent
    ],
    entryComponents: [BillingComponent, BillingUpdateComponent, BillingDeleteDialogComponent, BillingDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrmsBillingModule {}
