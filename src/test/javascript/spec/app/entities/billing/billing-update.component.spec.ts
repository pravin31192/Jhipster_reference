/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PrmsTestModule } from '../../../test.module';
import { BillingUpdateComponent } from 'app/entities/billing/billing-update.component';
import { BillingService } from 'app/entities/billing/billing.service';
import { Billing } from 'app/shared/model/billing.model';

describe('Component Tests', () => {
    describe('Billing Management Update Component', () => {
        let comp: BillingUpdateComponent;
        let fixture: ComponentFixture<BillingUpdateComponent>;
        let service: BillingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrmsTestModule],
                declarations: [BillingUpdateComponent]
            })
                .overrideTemplate(BillingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BillingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillingService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Billing(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.billing = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Billing();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.billing = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
