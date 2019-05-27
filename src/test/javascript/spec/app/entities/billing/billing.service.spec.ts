/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BillingService } from 'app/entities/billing/billing.service';
import { IBilling, Billing } from 'app/shared/model/billing.model';

describe('Service Tests', () => {
    describe('Billing Service', () => {
        let injector: TestBed;
        let service: BillingService;
        let httpMock: HttpTestingController;
        let elemDefault: IBilling;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(BillingService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Billing(0, 0, 0, 0, 0, 0, 0, 0, 'BBBBBB', 'BBBBBB', 'BBBBBB', currentDate, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
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

            it('should create a Billing', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createdAt: currentDate.format(DATE_FORMAT),
                        updatedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdAt: currentDate,
                        updatedAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Billing(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Billing', async () => {
                const returnedFromService = Object.assign(
                    {
                        clientId: 1,
                        project: 1,
                        resource: 1,
                        hoursAllocated: 1,
                        billRate: 1,
                        salary: 1,
                        otherCost: 1,
                        clientName: 'BBBBBB',
                        projectName: 'BBBBBB',
                        resourceName: 'BBBBBB',
                        createdAt: currentDate.format(DATE_FORMAT),
                        updatedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
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

            it('should return a list of Billing', async () => {
                const returnedFromService = Object.assign(
                    {
                        clientId: 1,
                        project: 1,
                        resource: 1,
                        hoursAllocated: 1,
                        billRate: 1,
                        salary: 1,
                        otherCost: 1,
                        clientName: 'BBBBBB',
                        projectName: 'BBBBBB',
                        resourceName: 'BBBBBB',
                        createdAt: currentDate.format(DATE_FORMAT),
                        updatedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
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

            it('should delete a Billing', async () => {
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
