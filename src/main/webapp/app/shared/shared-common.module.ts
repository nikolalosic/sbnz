import { NgModule } from '@angular/core';

import { SbnzSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [SbnzSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [SbnzSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class SbnzSharedCommonModule {}
