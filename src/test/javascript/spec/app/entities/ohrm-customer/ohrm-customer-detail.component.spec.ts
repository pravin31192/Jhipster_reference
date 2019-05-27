/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrmsTestModule } from '../../../test.module';
import { OhrmCustomerDetailComponent } from 'app/entities/ohrm-customer/ohrm-customer-detail.component';
import { OhrmCustomer } from 'app/shared/model/ohrm-customer.model';

describe('Component Tests', () => {
    describe('OhrmCustomer Management Detail Component', () => {
        let comp: OhrmCustomerDetailComponent;
        let fixture: ComponentFixture<OhrmCustomerDetailComponent>;
        const route = ({ data: of({ ohrmCustomer: new OhrmCustomer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrmsTestModule],
                declarations: [OhrmCustomerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OhrmCustomerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OhrmCustomerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ohrmCustomer).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
