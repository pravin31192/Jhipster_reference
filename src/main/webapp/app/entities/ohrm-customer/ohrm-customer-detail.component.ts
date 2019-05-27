import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOhrmCustomer } from 'app/shared/model/ohrm-customer.model';

@Component({
    selector: 'jhi-ohrm-customer-detail',
    templateUrl: './ohrm-customer-detail.component.html'
})
export class OhrmCustomerDetailComponent implements OnInit {
    ohrmCustomer: IOhrmCustomer;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ohrmCustomer }) => {
            this.ohrmCustomer = ohrmCustomer;
        });
    }

    previousState() {
        window.history.back();
    }
}
