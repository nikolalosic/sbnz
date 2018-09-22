/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SbnzTestModule } from '../../../test.module';
import { DiseaseDetailComponent } from 'app/entities/disease/disease-detail.component';
import { Disease } from 'app/shared/model/disease.model';

describe('Component Tests', () => {
    describe('Disease Management Detail Component', () => {
        let comp: DiseaseDetailComponent;
        let fixture: ComponentFixture<DiseaseDetailComponent>;
        const route = ({ data: of({ disease: new Disease(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [DiseaseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DiseaseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DiseaseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.disease).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
