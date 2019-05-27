import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PrmsProjectModule } from './project/project.module';
import { PrmsBillingModule } from './billing/billing.module';
import { PrmsProjectResourceAssignModule } from './project-resource-assign/project-resource-assign.module';
import { PrmsRolesModule } from './roles/roles.module';
import { PrmsRoleUserMappingModule } from './role-user-mapping/role-user-mapping.module';
import { PrmsEmployeeSalaryModule } from './employee-salary/employee-salary.module';
import { ReportsModule } from 'app/entities/reports/reports.module';
import { PrmsOhrmCustomerModule } from './ohrm-customer/ohrm-customer.module';
// import { ReactiveFormsModule, FormsModule } from '@angular/forms';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        PrmsProjectModule,
        PrmsBillingModule,
        PrmsProjectResourceAssignModule,
        PrmsRolesModule,
        PrmsRoleUserMappingModule,
        PrmsEmployeeSalaryModule,
        ReportsModule,
        PrmsOhrmCustomerModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
        // ReactiveFormsModule,
        // FormsModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrmsEntityModule {}
