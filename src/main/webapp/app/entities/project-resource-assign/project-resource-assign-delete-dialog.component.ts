import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectResourceAssign } from 'app/shared/model/project-resource-assign.model';
import { ProjectResourceAssignService } from './project-resource-assign.service';

@Component({
    selector: 'jhi-project-resource-assign-delete-dialog',
    templateUrl: './project-resource-assign-delete-dialog.component.html'
})
export class ProjectResourceAssignDeleteDialogComponent {
    projectResourceAssign: IProjectResourceAssign;

    constructor(
        protected projectResourceAssignService: ProjectResourceAssignService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        console.log("Release POP UP with Id "+ id);
        this.projectResourceAssignService.releaseResourceFromProject(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'projectResourceAssignListModification',
                content: 'Resource Released'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-project-resource-assign-delete-popup',
    template: ''
})
export class ProjectResourceAssignDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ projectResourceAssign }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProjectResourceAssignDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.projectResourceAssign = projectResourceAssign;
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
