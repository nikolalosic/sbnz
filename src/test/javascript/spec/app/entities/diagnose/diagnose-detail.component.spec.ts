/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SbnzTestModule } from '../../../test.module';
import { DiagnoseDetailComponent } from 'app/entities/diagnose/diagnose-detail.component';
import { Diagnose } from 'app/shared/model/diagnose.model';

describe('Component Tests', () => {
    describe('Diagnose Management Detail Component', () => {
        let comp: DiagnoseDetailComponent;
        let fixture: ComponentFixture<DiagnoseDetailComponent>;
        const route = ({ data: of({ diagnose: new Diagnose(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SbnzTestModule],
                declarations: [DiagnoseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DiagnoseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DiagnoseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.diagnose).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
