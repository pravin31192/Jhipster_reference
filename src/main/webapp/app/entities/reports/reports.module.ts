import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
// import {
//         ResourceByDepartmentComponent,
//         ResourceReportByClientComponent,
//         ResourceReportByProjectComponent,
//         ResourceReportByPercentageComponent
// } from '../';
import {
    ProjectResourceAssignDetailComponent,
    ProjectResourceAssignUpdateComponent,
    ProjectResourceAssignDeletePopupComponent,
    ProjectResourceAssignDeleteDialogComponent,
    projectResourceAssignRoute,
    projectResourceAssignPopupRoute
} from '../project-resource-assign';

import { PrmsSharedModule } from 'app/shared';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ResourceByDepartmentComponent } from './resource-by-department.component';
import { ResourceReportByClientComponent } from './resource-report-by-client.component';
import { ResourceReportByProjectComponent } from './resource-report-by-project.component';
import { ResourceReportByPercentageComponent } from './resource-report-by-percentage.component';
import { reportsRoute } from './reports.route';
import { SunTechResourceStrengthComponent } from 'app/entities/reports/suntech-resource-strength.component';
import { ChartsModule } from 'ng2-charts';

const ENTITY_STATES = [...reportsRoute];

@NgModule({
    imports: [PrmsSharedModule, RouterModule.forChild(ENTITY_STATES), FormsModule, ReactiveFormsModule, ChartsModule],
    declarations: [
        ResourceReportByClientComponent,
        ResourceReportByProjectComponent,
        ResourceReportByPercentageComponent,
        ResourceByDepartmentComponent,
        SunTechResourceStrengthComponent
    ],
    entryComponents: [ResourceReportByProjectComponent, ResourceReportByPercentageComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReportsModule {}
