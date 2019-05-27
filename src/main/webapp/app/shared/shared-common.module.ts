import { NgModule } from '@angular/core';

import { PrmsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [PrmsSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [PrmsSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class PrmsSharedCommonModule {}
