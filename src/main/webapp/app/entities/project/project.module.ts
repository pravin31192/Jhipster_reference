import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrmsSharedModule } from 'app/shared';
import {
    ProjectComponent,
    ProjectDetailComponent,
    ProjectUpdateComponent,
    ProjectDeletePopupComponent,
    ProjectDeleteDialogComponent,
    projectRoute,
    projectPopupRoute
} from './';

const ENTITY_STATES = [...projectRoute, ...projectPopupRoute];

@NgModule({
    imports: [PrmsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProjectComponent,
        ProjectDetailComponent,
        ProjectUpdateComponent,
        ProjectDeleteDialogComponent,
        ProjectDeletePopupComponent
    ],
    entryComponents: [ProjectComponent, ProjectUpdateComponent, ProjectDeleteDialogComponent, ProjectDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrmsProjectModule {}
