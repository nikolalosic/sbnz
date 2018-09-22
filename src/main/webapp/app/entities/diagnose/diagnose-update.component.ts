import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IDiagnose } from 'app/shared/model/diagnose.model';
import { DiagnoseService } from './diagnose.service';
import { IDisease } from 'app/shared/model/disease.model';
import { DiseaseService } from 'app/entities/disease';
import { IUser, UserService } from 'app/core';
import { ISymptom } from 'app/shared/model/symptom.model';
import { SymptomService } from 'app/entities/symptom';
import { IMedicine } from 'app/shared/model/medicine.model';
import { MedicineService } from 'app/entities/medicine';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient';

@Component({
    selector: 'jhi-diagnose-update',
    templateUrl: './diagnose-update.component.html'
})
export class DiagnoseUpdateComponent implements OnInit {
    private _diagnose: IDiagnose;
    isSaving: boolean;

    diseases: IDisease[];

    users: IUser[];

    symptoms: ISymptom[];

    medicines: IMedicine[];

    patients: IPatient[];
    diagnoseDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private diagnoseService: DiagnoseService,
        private diseaseService: DiseaseService,
        private userService: UserService,
        private symptomService: SymptomService,
        private medicineService: MedicineService,
        private patientService: PatientService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ diagnose }) => {
            this.diagnose = diagnose;
        });
        this.diseaseService.query().subscribe(
            (res: HttpResponse<IDisease[]>) => {
                this.diseases = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.symptomService.query().subscribe(
            (res: HttpResponse<ISymptom[]>) => {
                this.symptoms = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.medicineService.query().subscribe(
            (res: HttpResponse<IMedicine[]>) => {
                this.medicines = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.patientService.query().subscribe(
            (res: HttpResponse<IPatient[]>) => {
                this.patients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.diagnose.diagnoseDate = moment(this.diagnoseDate, DATE_TIME_FORMAT);
        if (this.diagnose.id !== undefined) {
            this.subscribeToSaveResponse(this.diagnoseService.update(this.diagnose));
        } else {
            this.subscribeToSaveResponse(this.diagnoseService.create(this.diagnose));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDiagnose>>) {
        result.subscribe((res: HttpResponse<IDiagnose>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDiseaseById(index: number, item: IDisease) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackSymptomById(index: number, item: ISymptom) {
        return item.id;
    }

    trackMedicineById(index: number, item: IMedicine) {
        return item.id;
    }

    trackPatientById(index: number, item: IPatient) {
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
    get diagnose() {
        return this._diagnose;
    }

    set diagnose(diagnose: IDiagnose) {
        this._diagnose = diagnose;
        this.diagnoseDate = moment(diagnose.diagnoseDate).format(DATE_TIME_FORMAT);
    }
}
