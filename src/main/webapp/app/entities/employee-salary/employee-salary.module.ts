import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrmsSharedModule } from 'app/shared';
import {
    EmployeeSalaryComponent,
    EmployeeSalaryDetailComponent,
    EmployeeSalaryUpdateComponent,
    EmployeeSalaryDeletePopupComponent,
    EmployeeSalaryDeleteDialogComponent,
    employeeSalaryRoute,
    employeeSalaryPopupRoute
} from './';

const ENTITY_STATES = [...employeeSalaryRoute, ...employeeSalaryPopupRoute];

@NgModule({
    imports: [PrmsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EmployeeSalaryComponent,
        EmployeeSalaryDetailComponent,
        EmployeeSalaryUpdateComponent,
        EmployeeSalaryDeleteDialogComponent,
        EmployeeSalaryDeletePopupComponent
    ],
    entryComponents: [
        EmployeeSalaryComponent,
        EmployeeSalaryUpdateComponent,
        EmployeeSalaryDeleteDialogComponent,
        EmployeeSalaryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrmsEmployeeSalaryModule {}
