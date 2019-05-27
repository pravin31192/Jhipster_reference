import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrmsSharedModule } from 'app/shared';
import {
    OhrmCustomerComponent,
    OhrmCustomerDetailComponent,
    OhrmCustomerUpdateComponent,
    OhrmCustomerDeletePopupComponent,
    OhrmCustomerDeleteDialogComponent,
    ohrmCustomerRoute,
    ohrmCustomerPopupRoute
} from './';

const ENTITY_STATES = [...ohrmCustomerRoute, ...ohrmCustomerPopupRoute];

@NgModule({
    imports: [PrmsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OhrmCustomerComponent,
        OhrmCustomerDetailComponent,
        OhrmCustomerUpdateComponent,
        OhrmCustomerDeleteDialogComponent,
        OhrmCustomerDeletePopupComponent
    ],
    entryComponents: [
        OhrmCustomerComponent,
        OhrmCustomerUpdateComponent,
        OhrmCustomerDeleteDialogComponent,
        OhrmCustomerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrmsOhrmCustomerModule {}
