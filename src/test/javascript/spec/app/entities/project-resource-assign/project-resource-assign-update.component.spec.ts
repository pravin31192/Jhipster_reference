/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PrmsTestModule } from '../../../test.module';
import { ProjectResourceAssignUpdateComponent } from 'app/entities/project-resource-assign/project-resource-assign-update.component';
import { ProjectResourceAssignService } from 'app/entities/project-resource-assign/project-resource-assign.service';
import { ProjectResourceAssign } from 'app/shared/model/project-resource-assign.model';

describe('Component Tests', () => {
    describe('ProjectResourceAssign Management Update Component', () => {
        let comp: ProjectResourceAssignUpdateComponent;
        let fixture: ComponentFixture<ProjectResourceAssignUpdateComponent>;
        let service: ProjectResourceAssignService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrmsTestModule],
                declarations: [ProjectResourceAssignUpdateComponent]
            })
                .overrideTemplate(ProjectResourceAssignUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProjectResourceAssignUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectResourceAssignService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProjectResourceAssign(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.projectResourceAssign = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProjectResourceAssign();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.projectResourceAssign = entity;
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
