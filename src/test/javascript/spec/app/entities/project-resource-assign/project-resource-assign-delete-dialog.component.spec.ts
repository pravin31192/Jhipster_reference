/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrmsTestModule } from '../../../test.module';
import { ProjectResourceAssignDeleteDialogComponent } from 'app/entities/project-resource-assign/project-resource-assign-delete-dialog.component';
import { ProjectResourceAssignService } from 'app/entities/project-resource-assign/project-resource-assign.service';

describe('Component Tests', () => {
    describe('ProjectResourceAssign Management Delete Component', () => {
        let comp: ProjectResourceAssignDeleteDialogComponent;
        let fixture: ComponentFixture<ProjectResourceAssignDeleteDialogComponent>;
        let service: ProjectResourceAssignService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrmsTestModule],
                declarations: [ProjectResourceAssignDeleteDialogComponent]
            })
                .overrideTemplate(ProjectResourceAssignDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProjectResourceAssignDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectResourceAssignService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
