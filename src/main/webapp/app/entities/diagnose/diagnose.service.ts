import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import * as moment from 'moment';
import {DATE_FORMAT} from 'app/shared/constants/input.constants';
import {map} from 'rxjs/operators';

import {SERVER_API_URL} from 'app/app.constants';
import {createRequestOption} from 'app/shared';
import {IDiagnose} from 'app/shared/model/diagnose.model';
import {IDisease} from "app/shared/model/disease.model";

type EntityResponseType = HttpResponse<IDiagnose>;
type EntityArrayResponseType = HttpResponse<IDiagnose[]>;

@Injectable({providedIn: 'root'})
export class DiagnoseService {
    private resourceUrl = SERVER_API_URL + 'api/diagnoses';

    constructor(private http: HttpClient) {
    }

    create(diagnose: IDiagnose): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(diagnose);
        return this.http
            .post<IDiagnose>(this.resourceUrl, copy, {observe: 'response'})
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(diagnose: IDiagnose): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(diagnose);
        return this.http
            .put<IDiagnose>(this.resourceUrl, copy, {observe: 'response'})
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    findProbableDisease(disease): Observable<EntityResponseType> {
        return this.http
            .post<IDisease>(SERVER_API_URL + 'api/diagnoses/check', disease, {observe: 'response'})
            .pipe(map((res: EntityResponseType) => res));
    }

    showDiseasesBySymptom(disease): Observable<EntityArrayResponseType> {
            return this.http
                .post<IDisease[]>(SERVER_API_URL + 'api/diagnoses/filterBySymptoms', disease, {observe: 'response'})
                .pipe(map((res: EntityArrayResponseType) => res));

    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDiagnose>(`${this.resourceUrl}/${id}`, {observe: 'response'})
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDiagnose[]>(this.resourceUrl, {params: options, observe: 'response'})
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }

    private convertDateFromClient(diagnose: IDiagnose): IDiagnose {
        const copy: IDiagnose = Object.assign({}, diagnose, {
            diagnoseDate: diagnose.diagnoseDate != null && diagnose.diagnoseDate.isValid() ? diagnose.diagnoseDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.diagnoseDate = res.body.diagnoseDate != null ? moment(res.body.diagnoseDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((diagnose: IDiagnose) => {
            diagnose.diagnoseDate = diagnose.diagnoseDate != null ? moment(diagnose.diagnoseDate) : null;
        });
        return res;
    }
}
