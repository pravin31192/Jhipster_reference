import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IOhrmCustomer } from 'app/shared/model/ohrm-customer.model';
import { OhrmCustomerService } from './ohrm-customer.service';

@Component({
    selector: 'jhi-ohrm-customer-update',
    templateUrl: './ohrm-customer-update.component.html'
})
export class OhrmCustomerUpdateComponent implements OnInit {
    ohrmCustomer: IOhrmCustomer;
    isSaving: boolean;

    constructor(protected ohrmCustomerService: OhrmCustomerService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ohrmCustomer }) => {
            this.ohrmCustomer = ohrmCustomer;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ohrmCustomer.id !== undefined) {
            this.subscribeToSaveResponse(this.ohrmCustomerService.update(this.ohrmCustomer));
        } else {
            this.subscribeToSaveResponse(this.ohrmCustomerService.create(this.ohrmCustomer));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOhrmCustomer>>) {
        result.subscribe((res: HttpResponse<IOhrmCustomer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
