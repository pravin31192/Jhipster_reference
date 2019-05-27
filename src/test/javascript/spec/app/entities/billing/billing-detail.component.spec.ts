/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrmsTestModule } from '../../../test.module';
import { BillingDetailComponent } from 'app/entities/billing/billing-detail.component';
import { Billing } from 'app/shared/model/billing.model';

describe('Component Tests', () => {
    describe('Billing Management Detail Component', () => {
        let comp: BillingDetailComponent;
        let fixture: ComponentFixture<BillingDetailComponent>;
        const route = ({ data: of({ billing: new Billing(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrmsTestModule],
                declarations: [BillingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BillingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BillingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.billing).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
