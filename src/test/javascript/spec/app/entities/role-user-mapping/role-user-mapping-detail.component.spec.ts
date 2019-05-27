/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrmsTestModule } from '../../../test.module';
import { RoleUserMappingDetailComponent } from 'app/entities/role-user-mapping/role-user-mapping-detail.component';
import { RoleUserMapping } from 'app/shared/model/role-user-mapping.model';

describe('Component Tests', () => {
    describe('RoleUserMapping Management Detail Component', () => {
        let comp: RoleUserMappingDetailComponent;
        let fixture: ComponentFixture<RoleUserMappingDetailComponent>;
        const route = ({ data: of({ roleUserMapping: new RoleUserMapping(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrmsTestModule],
                declarations: [RoleUserMappingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RoleUserMappingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RoleUserMappingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.roleUserMapping).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
