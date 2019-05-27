/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrmsTestModule } from '../../../test.module';
import { ProjectResourceAssignDetailComponent } from 'app/entities/project-resource-assign/project-resource-assign-detail.component';
import { ProjectResourceAssign } from 'app/shared/model/project-resource-assign.model';

describe('Component Tests', () => {
    describe('ProjectResourceAssign Management Detail Component', () => {
        let comp: ProjectResourceAssignDetailComponent;
        let fixture: ComponentFixture<ProjectResourceAssignDetailComponent>;
        const route = ({ data: of({ projectResourceAssign: new ProjectResourceAssign(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrmsTestModule],
                declarations: [ProjectResourceAssignDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProjectResourceAssignDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProjectResourceAssignDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.projectResourceAssign).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
