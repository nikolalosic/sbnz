import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';

import {IPatient} from 'app/shared/model/patient.model';
import {Principal} from 'app/core';

import {ITEMS_PER_PAGE} from 'app/shared';
import {ReportService} from './report.service';

@Component({
    selector: 'jhi-report',
    templateUrl: './report.component.html'
})
export class ReportComponent implements OnInit, OnDestroy {
    currentAccount: any;
    patients: IPatient[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        private reportService: ReportService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
    }


    reportAddicts() {
        this.reportService
            .reportAddicts()
            .subscribe((res: HttpResponse<IPatient[]>) => {
                    this.patients = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    reportImmunity() {
        this.reportService
            .reportImmunity()
            .subscribe((res: HttpResponse<IPatient[]>) => {
                    this.patients = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    reportChronics() {
        this.reportService
            .reportChronics()
            .subscribe((res: HttpResponse<IPatient[]>) => {
                    this.patients = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }


    ngOnInit() {
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPatients();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPatient) {
        return item.id;
    }

    registerChangeInPatients() {
        this.eventSubscriber = this.eventManager.subscribe('patientListModification', response => {});
    }


    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
