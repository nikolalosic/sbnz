import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMedicine } from 'app/shared/model/medicine.model';
import { MedicineService } from './medicine.service';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from 'app/entities/ingredient';

@Component({
    selector: 'jhi-medicine-update',
    templateUrl: './medicine-update.component.html'
})
export class MedicineUpdateComponent implements OnInit {
    private _medicine: IMedicine;
    isSaving: boolean;

    ingredients: IIngredient[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private medicineService: MedicineService,
        private ingredientService: IngredientService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ medicine }) => {
            this.medicine = medicine;
        });
        this.ingredientService.query().subscribe(
            (res: HttpResponse<IIngredient[]>) => {
                this.ingredients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.medicine.id !== undefined) {
            this.subscribeToSaveResponse(this.medicineService.update(this.medicine));
        } else {
            this.subscribeToSaveResponse(this.medicineService.create(this.medicine));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMedicine>>) {
        result.subscribe((res: HttpResponse<IMedicine>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackIngredientById(index: number, item: IIngredient) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get medicine() {
        return this._medicine;
    }

    set medicine(medicine: IMedicine) {
        this._medicine = medicine;
    }
}
