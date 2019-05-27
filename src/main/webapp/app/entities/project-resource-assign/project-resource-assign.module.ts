import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrmsSharedModule } from 'app/shared';
import {
    ProjectResourceAssignComponent,
    ProjectResourceAssignDetailComponent,
    ProjectResourceAssignUpdateComponent,
    ProjectResourceAssignDeletePopupComponent,
    ProjectResourceAssignDeleteDialogComponent,
    projectResourceAssignRoute,
    projectResourceAssignPopupRoute
} from './';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProjectResourceAssignReportComponent } from './project-resource-assign-report.component';

const ENTITY_STATES = [...projectResourceAssignRoute, ...projectResourceAssignPopupRoute];

@NgModule({
    imports: [
        PrmsSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        FormsModule,
        ReactiveFormsModule
    ],
    declarations: [
        ProjectResourceAssignComponent,
        ProjectResourceAssignDetailComponent,
        ProjectResourceAssignUpdateComponent,
        ProjectResourceAssignDeleteDialogComponent,
        ProjectResourceAssignDeletePopupComponent,
        ProjectResourceAssignReportComponent
    ],
    entryComponents: [
        ProjectResourceAssignComponent,
        ProjectResourceAssignUpdateComponent,
        ProjectResourceAssignDeleteDialogComponent,
        ProjectResourceAssignDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrmsProjectResourceAssignModule {}
