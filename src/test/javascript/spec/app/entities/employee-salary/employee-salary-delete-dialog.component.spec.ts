/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrmsTestModule } from '../../../test.module';
import { EmployeeSalaryDeleteDialogComponent } from 'app/entities/employee-salary/employee-salary-delete-dialog.component';
import { EmployeeSalaryService } from 'app/entities/employee-salary/employee-salary.service';

describe('Component Tests', () => {
    describe('EmployeeSalary Management Delete Component', () => {
        let comp: EmployeeSalaryDeleteDialogComponent;
        let fixture: ComponentFixture<EmployeeSalaryDeleteDialogComponent>;
        let service: EmployeeSalaryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrmsTestModule],
                declarations: [EmployeeSalaryDeleteDialogComponent]
            })
                .overrideTemplate(EmployeeSalaryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EmployeeSalaryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeSalaryService);
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
