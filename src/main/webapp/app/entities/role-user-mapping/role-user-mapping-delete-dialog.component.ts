import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRoleUserMapping } from 'app/shared/model/role-user-mapping.model';
import { RoleUserMappingService } from './role-user-mapping.service';

@Component({
    selector: 'jhi-role-user-mapping-delete-dialog',
    templateUrl: './role-user-mapping-delete-dialog.component.html'
})
export class RoleUserMappingDeleteDialogComponent {
    roleUserMapping: IRoleUserMapping;

    constructor(
        protected roleUserMappingService: RoleUserMappingService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.roleUserMappingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'roleUserMappingListModification',
                content: 'Deleted an roleUserMapping'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-role-user-mapping-delete-popup',
    template: ''
})
export class RoleUserMappingDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ roleUserMapping }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RoleUserMappingDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.roleUserMapping = roleUserMapping;
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
