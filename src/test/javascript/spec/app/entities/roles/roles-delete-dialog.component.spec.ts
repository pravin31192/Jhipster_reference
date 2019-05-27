/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrmsTestModule } from '../../../test.module';
import { RolesDeleteDialogComponent } from 'app/entities/roles/roles-delete-dialog.component';
import { RolesService } from 'app/entities/roles/roles.service';

describe('Component Tests', () => {
    describe('Roles Management Delete Component', () => {
        let comp: RolesDeleteDialogComponent;
        let fixture: ComponentFixture<RolesDeleteDialogComponent>;
        let service: RolesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrmsTestModule],
                declarations: [RolesDeleteDialogComponent]
            })
                .overrideTemplate(RolesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RolesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RolesService);
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
