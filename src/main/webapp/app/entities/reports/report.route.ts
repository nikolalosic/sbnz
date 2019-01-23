import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ReportComponent } from './report.component';
import {IPatient, Patient} from "app/shared/model/patient.model";
import {PatientService} from "app/entities/patient";

@Injectable({ providedIn: 'root' })
export class ReportResolve implements Resolve<IPatient> {
    constructor(private service: PatientService) {}
    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((patient: HttpResponse<Patient>) => patient.body));
        }
        return of(new Patient());
    }
}

export const reportRoute: Routes = [
    {
        path: 'report',
        component: ReportComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_DOCTOR'],
            defaultSort: 'id,asc',
            pageTitle: 'Reports'
        },
        canActivate: [UserRouteAccessService]
    },

];

export const reportPopupRoute: Routes = [
];
