import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISymptom } from 'app/shared/model/symptom.model';
import { SymptomService } from './symptom.service';

@Component({
    selector: 'jhi-symptom-update',
    templateUrl: './symptom-update.component.html'
})
export class SymptomUpdateComponent implements OnInit {
    private _symptom: ISymptom;
    isSaving: boolean;

    constructor(private symptomService: SymptomService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ symptom }) => {
            this.symptom = symptom;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.symptom.id !== undefined) {
            this.subscribeToSaveResponse(this.symptomService.update(this.symptom));
        } else {
            this.subscribeToSaveResponse(this.symptomService.create(this.symptom));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISymptom>>) {
        result.subscribe((res: HttpResponse<ISymptom>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get symptom() {
        return this._symptom;
    }

    set symptom(symptom: ISymptom) {
        this._symptom = symptom;
    }
}
