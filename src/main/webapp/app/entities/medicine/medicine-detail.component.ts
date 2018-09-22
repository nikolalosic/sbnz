import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedicine } from 'app/shared/model/medicine.model';

@Component({
    selector: 'jhi-medicine-detail',
    templateUrl: './medicine-detail.component.html'
})
export class MedicineDetailComponent implements OnInit {
    medicine: IMedicine;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ medicine }) => {
            this.medicine = medicine;
        });
    }

    previousState() {
        window.history.back();
    }
}
