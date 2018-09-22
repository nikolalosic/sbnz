import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from './patient.service';
import { IMedicine } from 'app/shared/model/medicine.model';
import { MedicineService } from 'app/entities/medicine';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from 'app/entities/ingredient';

@Component({
    selector: 'jhi-patient-update',
    templateUrl: './patient-update.component.html'
})
export class PatientUpdateComponent implements OnInit {
    private _patient: IPatient;
    isSaving: boolean;

    medicines: IMedicine[];

    ingredients: IIngredient[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private patientService: PatientService,
        private medicineService: MedicineService,
        private ingredientService: IngredientService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ patient }) => {
            this.patient = patient;
        });
        this.medicineService.query().subscribe(
            (res: HttpResponse<IMedicine[]>) => {
                this.medicines = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.patient.id !== undefined) {
            this.subscribeToSaveResponse(this.patientService.update(this.patient));
        } else {
            this.subscribeToSaveResponse(this.patientService.create(this.patient));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPatient>>) {
        result.subscribe((res: HttpResponse<IPatient>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMedicineById(index: number, item: IMedicine) {
        return item.id;
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
    get patient() {
        return this._patient;
    }

    set patient(patient: IPatient) {
        this._patient = patient;
    }
}
