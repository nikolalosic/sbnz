import { IDiagnose } from 'app/shared/model//diagnose.model';
import { IMedicine } from 'app/shared/model//medicine.model';
import { IIngredient } from 'app/shared/model//ingredient.model';

export interface IPatient {
    id?: number;
    firstName?: string;
    lastName?: string;
    diagnoses?: IDiagnose[];
    allergicMedicines?: IMedicine[];
    allergicIngredients?: IIngredient[];
}

export class Patient implements IPatient {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public diagnoses?: IDiagnose[],
        public allergicMedicines?: IMedicine[],
        public allergicIngredients?: IIngredient[]
    ) {}
}
