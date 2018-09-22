import { IIngredient } from 'app/shared/model//ingredient.model';

export const enum MedicineType {
    ANTIBIOTIC = 'ANTIBIOTIC',
    ANELGETIC = 'ANELGETIC',
    OTHER = 'OTHER'
}

export interface IMedicine {
    id?: number;
    name?: string;
    type?: MedicineType;
    ingredients?: IIngredient[];
}

export class Medicine implements IMedicine {
    constructor(public id?: number, public name?: string, public type?: MedicineType, public ingredients?: IIngredient[]) {}
}
