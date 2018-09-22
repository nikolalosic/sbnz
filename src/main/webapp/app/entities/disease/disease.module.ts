import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbnzSharedModule } from 'app/shared';
import {
    DiseaseComponent,
    DiseaseDetailComponent,
    DiseaseUpdateComponent,
    DiseaseDeletePopupComponent,
    DiseaseDeleteDialogComponent,
    diseaseRoute,
    diseasePopupRoute
} from './';

const ENTITY_STATES = [...diseaseRoute, ...diseasePopupRoute];

@NgModule({
    imports: [SbnzSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DiseaseComponent,
        DiseaseDetailComponent,
        DiseaseUpdateComponent,
        DiseaseDeleteDialogComponent,
        DiseaseDeletePopupComponent
    ],
    entryComponents: [DiseaseComponent, DiseaseUpdateComponent, DiseaseDeleteDialogComponent, DiseaseDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbnzDiseaseModule {}
