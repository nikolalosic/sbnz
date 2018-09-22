/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SbnzTestModule } from '../../../test.module';
import { DiagnoseDeleteDialogComponent } from 'app/entities/diagnose/diagnose-delete-dialog.component';
import { DiagnoseService } from 'app/entities/diagnose/diagnose.service';

describe('Component Tests', () => {
    describe('Diagnose Management Delete Component', () => {
        let comp: DiagnoseDeleteDialogComponent;
        let fixture: ComponentFixture<DiagnoseDeleteDialogComponent>;
        let service: DiagnoseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [DiagnoseDeleteDialogComponent]
            })
                .overrideTemplate(DiagnoseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DiagnoseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiagnoseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
