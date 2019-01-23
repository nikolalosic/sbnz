import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbnzSharedModule } from 'app/shared';
import {
    ReportComponent,
    reportRoute,
    reportPopupRoute
} from './';

const ENTITY_STATES = [...reportRoute, ...reportPopupRoute];

@NgModule({
    imports: [SbnzSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReportComponent
    ],
    entryComponents: [ReportComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbnzReportModule {}
