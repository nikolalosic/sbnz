import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SbnzSharedModule } from 'app/shared';
import {
    MedicineComponent,
    MedicineDetailComponent,
    MedicineUpdateComponent,
    MedicineDeletePopupComponent,
    MedicineDeleteDialogComponent,
    medicineRoute,
    medicinePopupRoute
} from './';

const ENTITY_STATES = [...medicineRoute, ...medicinePopupRoute];

@NgModule({
    imports: [SbnzSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MedicineComponent,
        MedicineDetailComponent,
        MedicineUpdateComponent,
        MedicineDeleteDialogComponent,
        MedicineDeletePopupComponent
    ],
    entryComponents: [MedicineComponent, MedicineUpdateComponent, MedicineDeleteDialogComponent, MedicineDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbnzMedicineModule {}
