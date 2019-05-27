/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { RoleUserMappingService } from 'app/entities/role-user-mapping/role-user-mapping.service';
import { IRoleUserMapping, RoleUserMapping } from 'app/shared/model/role-user-mapping.model';

describe('Service Tests', () => {
    describe('RoleUserMapping Service', () => {
        let injector: TestBed;
        let service: RoleUserMappingService;
        let httpMock: HttpTestingController;
        let elemDefault: IRoleUserMapping;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(RoleUserMappingService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new RoleUserMapping(0, 0, 0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 0);
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

            it('should create a RoleUserMapping', async () => {
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
                    .create(new RoleUserMapping(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a RoleUserMapping', async () => {
                const returnedFromService = Object.assign(
                    {
                        userId: 1,
                        roleId: 1,
                        userName: 'BBBBBB',
                        roleName: 'BBBBBB',
                        createdAt: currentDate.format(DATE_FORMAT),
                        updatedAt: currentDate.format(DATE_FORMAT),
                        createdBy: 1
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

            it('should return a list of RoleUserMapping', async () => {
                const returnedFromService = Object.assign(
                    {
                        userId: 1,
                        roleId: 1,
                        userName: 'BBBBBB',
                        roleName: 'BBBBBB',
                        createdAt: currentDate.format(DATE_FORMAT),
                        updatedAt: currentDate.format(DATE_FORMAT),
                        createdBy: 1
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

            it('should delete a RoleUserMapping', async () => {
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
