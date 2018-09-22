import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDisease } from 'app/shared/model/disease.model';

@Component({
    selector: 'jhi-disease-detail',
    templateUrl: './disease-detail.component.html'
})
export class DiseaseDetailComponent implements OnInit {
    disease: IDisease;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ disease }) => {
            this.disease = disease;
        });
    }

    previousState() {
        window.history.back();
    }
}
