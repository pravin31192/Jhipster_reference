import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOhrmCustomer } from 'app/shared/model/ohrm-customer.model';
import { OhrmCustomerService } from './ohrm-customer.service';

@Component({
    selector: 'jhi-ohrm-customer-delete-dialog',
    templateUrl: './ohrm-customer-delete-dialog.component.html'
})
export class OhrmCustomerDeleteDialogComponent {
    ohrmCustomer: IOhrmCustomer;

    constructor(
        protected ohrmCustomerService: OhrmCustomerService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ohrmCustomerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ohrmCustomerListModification',
                content: 'Deleted an ohrmCustomer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ohrm-customer-delete-popup',
    template: ''
})
export class OhrmCustomerDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ohrmCustomer }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OhrmCustomerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.ohrmCustomer = ohrmCustomer;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
