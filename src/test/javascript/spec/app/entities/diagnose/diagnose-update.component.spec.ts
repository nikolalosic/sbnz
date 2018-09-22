/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SbnzTestModule } from '../../../test.module';
import { DiagnoseUpdateComponent } from 'app/entities/diagnose/diagnose-update.component';
import { DiagnoseService } from 'app/entities/diagnose/diagnose.service';
import { Diagnose } from 'app/shared/model/diagnose.model';

describe('Component Tests', () => {
    describe('Diagnose Management Update Component', () => {
        let comp: DiagnoseUpdateComponent;
        let fixture: ComponentFixture<DiagnoseUpdateComponent>;
        let service: DiagnoseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [DiagnoseUpdateComponent]
            })
                .overrideTemplate(DiagnoseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DiagnoseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiagnoseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Diagnose(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.diagnose = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Diagnose();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.diagnose = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
