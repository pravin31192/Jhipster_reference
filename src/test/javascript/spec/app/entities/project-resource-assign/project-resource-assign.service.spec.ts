/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProjectResourceAssignService } from 'app/entities/project-resource-assign/project-resource-assign.service';
import { IProjectResourceAssign, ProjectResourceAssign } from 'app/shared/model/project-resource-assign.model';

describe('Service Tests', () => {
    describe('ProjectResourceAssign Service', () => {
        let injector: TestBed;
        let service: ProjectResourceAssignService;
        let httpMock: HttpTestingController;
        let elemDefault: IProjectResourceAssign;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ProjectResourceAssignService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new ProjectResourceAssign(
                0,
                0,
                0,
                0,
                currentDate,
                currentDate,
                0,
                0,
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                0,
                0,
                0,
                currentDate,
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fromDate: currentDate.format(DATE_FORMAT),
                        toDate: currentDate.format(DATE_FORMAT),
                        createdAt: currentDate.format(DATE_FORMAT),
                        updatedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a ProjectResourceAssign', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        fromDate: currentDate.format(DATE_FORMAT),
                        toDate: currentDate.format(DATE_FORMAT),
                        createdAt: currentDate.format(DATE_FORMAT),
                        updatedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fromDate: currentDate,
                        toDate: currentDate,
                        createdAt: currentDate,
                        updatedAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new ProjectResourceAssign(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ProjectResourceAssign', async () => {
                const returnedFromService = Object.assign(
                    {
                        projectId: 1,
                        empId: 1,
                        role: 1,
                        fromDate: currentDate.format(DATE_FORMAT),
                        toDate: currentDate.format(DATE_FORMAT),
                        billingType: 1,
                        billValue: 1,
                        percentageOfWork: 1,
                        projectName: 'BBBBBB',
                        empName: 'BBBBBB',
                        roleName: 'BBBBBB',
                        clientName: 'BBBBBB',
                        estimatedStaffHours: 1,
                        actualStaffHours: 1,
                        location: 1,
                        status: 1,
                        createdAt: currentDate.format(DATE_FORMAT),
                        updatedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        fromDate: currentDate,
                        toDate: currentDate,
                        createdAt: currentDate,
                        updatedAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of ProjectResourceAssign', async () => {
                const returnedFromService = Object.assign(
                    {
                        projectId: 1,
                        empId: 1,
                        role: 1,
                        fromDate: currentDate.format(DATE_FORMAT),
                        toDate: currentDate.format(DATE_FORMAT),
                        billingType: 1,
                        billValue: 1,
                        percentageOfWork: 1,
                        projectName: 'BBBBBB',
                        empName: 'BBBBBB',
                        roleName: 'BBBBBB',
                        clientName: 'BBBBBB',
                        estimatedStaffHours: 1,
                        actualStaffHours: 1,
                        location: 1,
                        status: 1,
                        createdAt: currentDate.format(DATE_FORMAT),
                        updatedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fromDate: currentDate,
                        toDate: currentDate,
                        createdAt: currentDate,
                        updatedAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a ProjectResourceAssign', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
