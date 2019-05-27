import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrmsSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { ChartsModule } from 'ng2-charts';

@NgModule({
    imports: [
        PrmsSharedModule,
        RouterModule.forChild([HOME_ROUTE]),
        ChartsModule
    ],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrmsHomeModule {}
