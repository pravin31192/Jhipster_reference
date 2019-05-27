/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PrmsTestModule } from '../../../test.module';
import { OhrmCustomerUpdateComponent } from 'app/entities/ohrm-customer/ohrm-customer-update.component';
import { OhrmCustomerService } from 'app/entities/ohrm-customer/ohrm-customer.service';
import { OhrmCustomer } from 'app/shared/model/ohrm-customer.model';

describe('Component Tests', () => {
    describe('OhrmCustomer Management Update Component', () => {
        let comp: OhrmCustomerUpdateComponent;
        let fixture: ComponentFixture<OhrmCustomerUpdateComponent>;
        let service: OhrmCustomerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrmsTestModule],
                declarations: [OhrmCustomerUpdateComponent]
            })
                .overrideTemplate(OhrmCustomerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OhrmCustomerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OhrmCustomerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OhrmCustomer(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ohrmCustomer = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OhrmCustomer();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ohrmCustomer = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
