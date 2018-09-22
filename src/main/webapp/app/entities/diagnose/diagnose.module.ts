import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbnzSharedModule } from 'app/shared';
import { SbnzAdminModule } from 'app/admin/admin.module';
import {
    DiagnoseComponent,
    DiagnoseDetailComponent,
    DiagnoseUpdateComponent,
    DiagnoseDeletePopupComponent,
    DiagnoseDeleteDialogComponent,
    diagnoseRoute,
    diagnosePopupRoute
} from './';

const ENTITY_STATES = [...diagnoseRoute, ...diagnosePopupRoute];

@NgModule({
    imports: [SbnzSharedModule, SbnzAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DiagnoseComponent,
        DiagnoseDetailComponent,
        DiagnoseUpdateComponent,
        DiagnoseDeleteDialogComponent,
        DiagnoseDeletePopupComponent
    ],
    entryComponents: [DiagnoseComponent, DiagnoseUpdateComponent, DiagnoseDeleteDialogComponent, DiagnoseDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbnzDiagnoseModule {}
