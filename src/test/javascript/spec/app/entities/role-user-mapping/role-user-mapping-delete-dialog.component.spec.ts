/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrmsTestModule } from '../../../test.module';
import { RoleUserMappingDeleteDialogComponent } from 'app/entities/role-user-mapping/role-user-mapping-delete-dialog.component';
import { RoleUserMappingService } from 'app/entities/role-user-mapping/role-user-mapping.service';

describe('Component Tests', () => {
    describe('RoleUserMapping Management Delete Component', () => {
        let comp: RoleUserMappingDeleteDialogComponent;
        let fixture: ComponentFixture<RoleUserMappingDeleteDialogComponent>;
        let service: RoleUserMappingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrmsTestModule],
                declarations: [RoleUserMappingDeleteDialogComponent]
            })
                .overrideTemplate(RoleUserMappingDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RoleUserMappingDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoleUserMappingService);
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
