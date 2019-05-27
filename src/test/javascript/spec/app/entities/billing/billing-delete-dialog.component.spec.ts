/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrmsTestModule } from '../../../test.module';
import { BillingDeleteDialogComponent } from 'app/entities/billing/billing-delete-dialog.component';
import { BillingService } from 'app/entities/billing/billing.service';

describe('Component Tests', () => {
    describe('Billing Management Delete Component', () => {
        let comp: BillingDeleteDialogComponent;
        let fixture: ComponentFixture<BillingDeleteDialogComponent>;
        let service: BillingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrmsTestModule],
                declarations: [BillingDeleteDialogComponent]
            })
                .overrideTemplate(BillingDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BillingDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillingService);
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
