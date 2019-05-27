import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBilling } from 'app/shared/model/billing.model';

@Component({
    selector: 'jhi-billing-detail',
    templateUrl: './billing-detail.component.html'
})
export class BillingDetailComponent implements OnInit {
    billing: IBilling;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ billing }) => {
            this.billing = billing;
        });
    }

    previousState() {
        window.history.back();
    }
}
