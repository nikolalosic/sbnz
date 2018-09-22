import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Diagnose } from 'app/shared/model/diagnose.model';
import { DiagnoseService } from './diagnose.service';
import { DiagnoseComponent } from './diagnose.component';
import { DiagnoseDetailComponent } from './diagnose-detail.component';
import { DiagnoseUpdateComponent } from './diagnose-update.component';
import { DiagnoseDeletePopupComponent } from './diagnose-delete-dialog.component';
import { IDiagnose } from 'app/shared/model/diagnose.model';

@Injectable({ providedIn: 'root' })
export class DiagnoseResolve implements Resolve<IDiagnose> {
    constructor(private service: DiagnoseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((diagnose: HttpResponse<Diagnose>) => diagnose.body));
        }
        return of(new Diagnose());
    }
}

export const diagnoseRoute: Routes = [
    {
        path: 'diagnose',
        component: DiagnoseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Diagnoses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'diagnose/:id/view',
        component: DiagnoseDetailComponent,
        resolve: {
            diagnose: DiagnoseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diagnoses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'diagnose/new',
        component: DiagnoseUpdateComponent,
        resolve: {
            diagnose: DiagnoseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diagnoses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'diagnose/:id/edit',
        component: DiagnoseUpdateComponent,
        resolve: {
            diagnose: DiagnoseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diagnoses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const diagnosePopupRoute: Routes = [
    {
        path: 'diagnose/:id/delete',
        component: DiagnoseDeletePopupComponent,
        resolve: {
            diagnose: DiagnoseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Diagnoses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
