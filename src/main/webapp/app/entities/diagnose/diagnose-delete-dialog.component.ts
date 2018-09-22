import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDiagnose } from 'app/shared/model/diagnose.model';
import { DiagnoseService } from './diagnose.service';

@Component({
    selector: 'jhi-diagnose-delete-dialog',
    templateUrl: './diagnose-delete-dialog.component.html'
})
export class DiagnoseDeleteDialogComponent {
    diagnose: IDiagnose;

    constructor(private diagnoseService: DiagnoseService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.diagnoseService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'diagnoseListModification',
                content: 'Deleted an diagnose'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-diagnose-delete-popup',
    template: ''
})
export class DiagnoseDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ diagnose }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DiagnoseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.diagnose = diagnose;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
