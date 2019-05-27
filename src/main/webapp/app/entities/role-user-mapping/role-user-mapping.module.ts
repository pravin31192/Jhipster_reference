import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrmsSharedModule } from 'app/shared';
import {
    RoleUserMappingComponent,
    RoleUserMappingDetailComponent,
    RoleUserMappingUpdateComponent,
    RoleUserMappingDeletePopupComponent,
    RoleUserMappingDeleteDialogComponent,
    roleUserMappingRoute,
    roleUserMappingPopupRoute
} from './';

const ENTITY_STATES = [...roleUserMappingRoute, ...roleUserMappingPopupRoute];

@NgModule({
    imports: [PrmsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RoleUserMappingComponent,
        RoleUserMappingDetailComponent,
        RoleUserMappingUpdateComponent,
        RoleUserMappingDeleteDialogComponent,
        RoleUserMappingDeletePopupComponent
    ],
    entryComponents: [
        RoleUserMappingComponent,
        RoleUserMappingUpdateComponent,
        RoleUserMappingDeleteDialogComponent,
        RoleUserMappingDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrmsRoleUserMappingModule {}
