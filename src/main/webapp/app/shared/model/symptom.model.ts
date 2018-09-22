export interface ISymptom {
    id?: number;
    name?: string;
}

export class Symptom implements ISymptom {
    constructor(public id?: number, public name?: string) {}
}
