import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Medicine } from 'app/shared/model/medicine.model';
import { MedicineService } from './medicine.service';
import { MedicineComponent } from './medicine.component';
import { MedicineDetailComponent } from './medicine-detail.component';
import { MedicineUpdateComponent } from './medicine-update.component';
import { MedicineDeletePopupComponent } from './medicine-delete-dialog.component';
import { IMedicine } from 'app/shared/model/medicine.model';

@Injectable({ providedIn: 'root' })
export class MedicineResolve implements Resolve<IMedicine> {
    constructor(private service: MedicineService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((medicine: HttpResponse<Medicine>) => medicine.body));
        }
        return of(new Medicine());
    }
}

export const medicineRoute: Routes = [
    {
        path: 'medicine',
        component: MedicineComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Medicines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'medicine/:id/view',
        component: MedicineDetailComponent,
        resolve: {
            medicine: MedicineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medicines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'medicine/new',
        component: MedicineUpdateComponent,
        resolve: {
            medicine: MedicineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medicines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'medicine/:id/edit',
        component: MedicineUpdateComponent,
        resolve: {
            medicine: MedicineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medicines'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const medicinePopupRoute: Routes = [
    {
        path: 'medicine/:id/delete',
        component: MedicineDeletePopupComponent,
        resolve: {
            medicine: MedicineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medicines'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
