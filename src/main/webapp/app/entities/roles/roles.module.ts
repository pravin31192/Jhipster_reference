import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrmsSharedModule } from 'app/shared';
import {
    RolesComponent,
    RolesDetailComponent,
    RolesUpdateComponent,
    RolesDeletePopupComponent,
    RolesDeleteDialogComponent,
    rolesRoute,
    rolesPopupRoute
} from './';

const ENTITY_STATES = [...rolesRoute, ...rolesPopupRoute];

@NgModule({
    imports: [PrmsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RolesComponent, RolesDetailComponent, RolesUpdateComponent, RolesDeleteDialogComponent, RolesDeletePopupComponent],
    entryComponents: [RolesComponent, RolesUpdateComponent, RolesDeleteDialogComponent, RolesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrmsRolesModule {}
