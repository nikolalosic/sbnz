import { Moment } from 'moment';
import { ISymptom } from 'app/shared/model//symptom.model';
import { IMedicine } from 'app/shared/model//medicine.model';

export interface IDiagnose {
    id?: number;
    diagnoseDate?: Moment;
    diseaseId?: number;
    doctorId?: number;
    symptoms?: ISymptom[];
    medicines?: IMedicine[];
    patientId?: number;
}

export class Diagnose implements IDiagnose {
    constructor(
        public id?: number,
        public diagnoseDate?: Moment,
        public diseaseId?: number,
        public doctorId?: number,
        public symptoms?: ISymptom[],
        public medicines?: IMedicine[],
        public patientId?: number
    ) {}
}
