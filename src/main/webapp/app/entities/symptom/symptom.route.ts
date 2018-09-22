import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Symptom } from 'app/shared/model/symptom.model';
import { SymptomService } from './symptom.service';
import { SymptomComponent } from './symptom.component';
import { SymptomDetailComponent } from './symptom-detail.component';
import { SymptomUpdateComponent } from './symptom-update.component';
import { SymptomDeletePopupComponent } from './symptom-delete-dialog.component';
import { ISymptom } from 'app/shared/model/symptom.model';

@Injectable({ providedIn: 'root' })
export class SymptomResolve implements Resolve<ISymptom> {
    constructor(private service: SymptomService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((symptom: HttpResponse<Symptom>) => symptom.body));
        }
        return of(new Symptom());
    }
}

export const symptomRoute: Routes = [
    {
        path: 'symptom',
        component: SymptomComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Symptoms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'symptom/:id/view',
        component: SymptomDetailComponent,
        resolve: {
            symptom: SymptomResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Symptoms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'symptom/new',
        component: SymptomUpdateComponent,
        resolve: {
            symptom: SymptomResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Symptoms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'symptom/:id/edit',
        component: SymptomUpdateComponent,
        resolve: {
            symptom: SymptomResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Symptoms'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const symptomPopupRoute: Routes = [
    {
        path: 'symptom/:id/delete',
        component: SymptomDeletePopupComponent,
        resolve: {
            symptom: SymptomResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Symptoms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
