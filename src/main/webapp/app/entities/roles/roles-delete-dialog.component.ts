import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRoles } from 'app/shared/model/roles.model';
import { RolesService } from './roles.service';

@Component({
    selector: 'jhi-roles-delete-dialog',
    templateUrl: './roles-delete-dialog.component.html'
})
export class RolesDeleteDialogComponent {
    roles: IRoles;

    constructor(protected rolesService: RolesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rolesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rolesListModification',
                content: 'Deleted an roles'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-roles-delete-popup',
    template: ''
})
export class RolesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ roles }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RolesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.roles = roles;
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
