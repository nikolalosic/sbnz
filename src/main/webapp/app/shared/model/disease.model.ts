import { ISymptom } from 'app/shared/model//symptom.model';

export interface IDisease {
    id?: number;
    name?: string;
    generalSymptoms?: ISymptom[];
    specificSymptoms?: ISymptom[];
}

export class Disease implements IDisease {
    constructor(public id?: number, public name?: string, public generalSymptoms?: ISymptom[], public specificSymptoms?: ISymptom[]) {}
}
