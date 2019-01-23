import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPatient } from 'app/shared/model/patient.model';

type EntityResponseType = HttpResponse<IPatient>;
type EntityArrayResponseType = HttpResponse<IPatient[]>;

@Injectable({ providedIn: 'root' })
export class ReportService {
    private resourceUrl = SERVER_API_URL + 'api/reports';

    constructor(private http: HttpClient) {}

    reportAddicts(): Observable<EntityArrayResponseType> {
        return this.http.get<IPatient[]>(this.resourceUrl + '/addictsReport', { params: {}, observe: 'response' });
    }

    reportChronics(): Observable<EntityArrayResponseType> {
        return this.http.get<IPatient[]>(this.resourceUrl + '/chronicsReport', { params: {}, observe: 'response' });
    }
    reportImmunity(): Observable<EntityArrayResponseType> {
        return this.http.get<IPatient[]>(this.resourceUrl + '/immunityReport', { params: {}, observe: 'response' });
    }

}
