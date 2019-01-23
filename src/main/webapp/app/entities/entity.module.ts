import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SbnzDiagnoseModule } from './diagnose/diagnose.module';
import { SbnzDiseaseModule } from './disease/disease.module';
import { SbnzIngredientModule } from './ingredient/ingredient.module';
import { SbnzMedicineModule } from './medicine/medicine.module';
import { SbnzSymptomModule } from './symptom/symptom.module';
import { SbnzPatientModule } from './patient/patient.module';
import {SbnzReportModule} from "app/entities/reports/report.module";
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SbnzDiagnoseModule,
        SbnzDiseaseModule,
        SbnzIngredientModule,
        SbnzMedicineModule,
        SbnzSymptomModule,
        SbnzPatientModule,
        SbnzReportModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SbnzEntityModule {}
