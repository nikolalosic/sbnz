import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbnzSharedModule } from 'app/shared';
import {
    SymptomComponent,
    SymptomDetailComponent,
    SymptomUpdateComponent,
    SymptomDeletePopupComponent,
    SymptomDeleteDialogComponent,
    symptomRoute,
    symptomPopupRoute
} from './';

const ENTITY_STATES = [...symptomRoute, ...symptomPopupRoute];

@NgModule({
    imports: [SbnzSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SymptomComponent,
        SymptomDetailComponent,
        SymptomUpdateComponent,
        SymptomDeleteDialogComponent,
        SymptomDeletePopupComponent
    ],
    entryComponents: [SymptomComponent, SymptomUpdateComponent, SymptomDeleteDialogComponent, SymptomDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbnzSymptomModule {}
