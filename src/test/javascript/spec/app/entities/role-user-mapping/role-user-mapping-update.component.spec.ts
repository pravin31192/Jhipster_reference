/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PrmsTestModule } from '../../../test.module';
import { RoleUserMappingUpdateComponent } from 'app/entities/role-user-mapping/role-user-mapping-update.component';
import { RoleUserMappingService } from 'app/entities/role-user-mapping/role-user-mapping.service';
import { RoleUserMapping } from 'app/shared/model/role-user-mapping.model';

describe('Component Tests', () => {
    describe('RoleUserMapping Management Update Component', () => {
        let comp: RoleUserMappingUpdateComponent;
        let fixture: ComponentFixture<RoleUserMappingUpdateComponent>;
        let service: RoleUserMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrmsTestModule],
                declarations: [RoleUserMappingUpdateComponent]
            })
                .overrideTemplate(RoleUserMappingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RoleUserMappingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoleUserMappingService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RoleUserMapping(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.roleUserMapping = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RoleUserMapping();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.roleUserMapping = entity;
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
