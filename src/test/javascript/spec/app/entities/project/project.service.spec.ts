/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProjectService } from 'app/entities/project/project.service';
import { IProject, Project } from 'app/shared/model/project.model';

describe('Service Tests', () => {
    describe('Project Service', () => {
        let injector: TestBed;
        let service: ProjectService;
        let httpMock: HttpTestingController;
        let elemDefault: IProject;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ProjectService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Project(
                0,
                'AAAAAAA',
                0,
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                0,
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
                currentDate,
                currentDate
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        startDate: currentDate.format(DATE_FORMAT),
                        endDate: currentDate.format(DATE_FORMAT),
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

            it('should create a Project', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        startDate: currentDate.format(DATE_FORMAT),
                        endDate: currentDate.format(DATE_FORMAT),
                        createdAt: currentDate.format(DATE_FORMAT),
                        updatedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        startDate: currentDate,
                        endDate: currentDate,
                        createdAt: currentDate,
                        updatedAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Project(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Project', async () => {
                const returnedFromService = Object.assign(
                    {
                        projectName: 'BBBBBB',
                        hrmProjectId: 1,
                        clientName: 'BBBBBB',
                        hrmClientId: 1,
                        clientCode: 'BBBBBB',
                        projectCode: 'BBBBBB',
                        billableValue: 1,
                        noOfResources: 1,
                        createdBy: 1,
                        status: 1,
                        type: 1,
                        startDate: currentDate.format(DATE_FORMAT),
                        endDate: currentDate.format(DATE_FORMAT),
                        estimatedStaffHours: 1,
                        actualStaffHours: 1,
                        percentageComplete: 1,
                        details: 'BBBBBB',
                        deliverables: 'BBBBBB',
                        attachments: 'BBBBBB',
                        createdAt: currentDate.format(DATE_FORMAT),
                        updatedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        startDate: currentDate,
                        endDate: currentDate,
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

            it('should return a list of Project', async () => {
                const returnedFromService = Object.assign(
                    {
                        projectName: 'BBBBBB',
                        hrmProjectId: 1,
                        clientName: 'BBBBBB',
                        hrmClientId: 1,
                        clientCode: 'BBBBBB',
                        projectCode: 'BBBBBB',
                        billableValue: 1,
                        noOfResources: 1,
                        createdBy: 1,
                        status: 1,
                        type: 1,
                        startDate: currentDate.format(DATE_FORMAT),
                        endDate: currentDate.format(DATE_FORMAT),
                        estimatedStaffHours: 1,
                        actualStaffHours: 1,
                        percentageComplete: 1,
                        details: 'BBBBBB',
                        deliverables: 'BBBBBB',
                        attachments: 'BBBBBB',
                        createdAt: currentDate.format(DATE_FORMAT),
                        updatedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        startDate: currentDate,
                        endDate: currentDate,
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

            it('should delete a Project', async () => {
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
