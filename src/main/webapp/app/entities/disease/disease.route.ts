import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Disease } from 'app/shared/model/disease.model';
import { DiseaseService } from './disease.service';
import { DiseaseComponent } from './disease.component';
import { DiseaseDetailComponent } from './disease-detail.component';
import { DiseaseUpdateComponent } from './disease-update.component';
import { DiseaseDeletePopupComponent } from './disease-delete-dialog.component';
import { IDisease } from 'app/shared/model/disease.model';

@Injectable({ providedIn: 'root' })
export class DiseaseResolve implements Resolve<IDisease> {
    constructor(private service: DiseaseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((disease: HttpResponse<Disease>) => disease.body));
        }
        return of(new Disease());
    }
}

export const diseaseRoute: Routes = [
    {
        path: 'disease',
        component: DiseaseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_DOCTOR'],
            defaultSort: 'id,asc',
            pageTitle: 'Diseases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'disease/:id/view',
        component: DiseaseDetailComponent,
        resolve: {
            disease: DiseaseResolve
        },
        data: {
            authorities: ['ROLE_DOCTOR', 'ROLE_ADMIN'],
            pageTitle: 'Diseases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'disease/new',
        component: DiseaseUpdateComponent,
        resolve: {
            disease: DiseaseResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Diseases'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'disease/:id/edit',
        component: DiseaseUpdateComponent,
        resolve: {
            disease: DiseaseResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Diseases'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const diseasePopupRoute: Routes = [
    {
        path: 'disease/:id/delete',
        component: DiseaseDeletePopupComponent,
        resolve: {
            disease: DiseaseResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Diseases'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
