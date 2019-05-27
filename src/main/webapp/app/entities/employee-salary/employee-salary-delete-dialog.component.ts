import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';
import { EmployeeSalaryService } from './employee-salary.service';

@Component({
    selector: 'jhi-employee-salary-delete-dialog',
    templateUrl: './employee-salary-delete-dialog.component.html'
})
export class EmployeeSalaryDeleteDialogComponent {
    employeeSalary: IEmployeeSalary;

    constructor(
        protected employeeSalaryService: EmployeeSalaryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employeeSalaryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'employeeSalaryListModification',
                content: 'Deleted an employeeSalary'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employee-salary-delete-popup',
    template: ''
})
export class EmployeeSalaryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ employeeSalary }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EmployeeSalaryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.employeeSalary = employeeSalary;
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
