import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDiagnose } from 'app/shared/model/diagnose.model';

@Component({
    selector: 'jhi-diagnose-detail',
    templateUrl: './diagnose-detail.component.html'
})
export class DiagnoseDetailComponent implements OnInit {
    diagnose: IDiagnose;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ diagnose }) => {
            this.diagnose = diagnose;
        });
    }

    previousState() {
        window.history.back();
    }
}
