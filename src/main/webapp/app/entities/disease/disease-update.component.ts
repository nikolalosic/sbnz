import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDisease } from 'app/shared/model/disease.model';
import { DiseaseService } from './disease.service';
import { ISymptom } from 'app/shared/model/symptom.model';
import { SymptomService } from 'app/entities/symptom';

@Component({
    selector: 'jhi-disease-update',
    templateUrl: './disease-update.component.html'
})
export class DiseaseUpdateComponent implements OnInit {
    private _disease: IDisease;
    isSaving: boolean;

    symptoms: ISymptom[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private diseaseService: DiseaseService,
        private symptomService: SymptomService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ disease }) => {
            this.disease = disease;
        });
        this.symptomService.query().subscribe(
            (res: HttpResponse<ISymptom[]>) => {
                this.symptoms = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.disease.id !== undefined) {
            this.subscribeToSaveResponse(this.diseaseService.update(this.disease));
        } else {
            this.subscribeToSaveResponse(this.diseaseService.create(this.disease));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDisease>>) {
        result.subscribe((res: HttpResponse<IDisease>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSymptomById(index: number, item: ISymptom) {
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
    get disease() {
        return this._disease;
    }

    set disease(disease: IDisease) {
        this._disease = disease;
    }
}
