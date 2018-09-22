/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SbnzTestModule } from '../../../test.module';
import { SymptomUpdateComponent } from 'app/entities/symptom/symptom-update.component';
import { SymptomService } from 'app/entities/symptom/symptom.service';
import { Symptom } from 'app/shared/model/symptom.model';

describe('Component Tests', () => {
    describe('Symptom Management Update Component', () => {
        let comp: SymptomUpdateComponent;
        let fixture: ComponentFixture<SymptomUpdateComponent>;
        let service: SymptomService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [SymptomUpdateComponent]
            })
                .overrideTemplate(SymptomUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SymptomUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SymptomService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Symptom(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.symptom = entity;
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
                    const entity = new Symptom();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.symptom = entity;
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
