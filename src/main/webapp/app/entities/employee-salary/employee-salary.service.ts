import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';

type EntityResponseType = HttpResponse<IEmployeeSalary>;
type EntityArrayResponseType = HttpResponse<IEmployeeSalary[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeSalaryService {
    public resourceUrl = SERVER_API_URL + 'api/employee-salaries';
    private subUnitsInHrmUrl = SERVER_API_URL + 'api/sub-units-in-hrm';
    private employeesInDepartmentUrl = SERVER_API_URL + 'api/employees-in-department';

    constructor(protected http: HttpClient) {}

    create(employeeSalary: IEmployeeSalary): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(employeeSalary);
        return this.http
            .post<IEmployeeSalary>(`${this.resourceUrl}/${employeeSalary.departmentId}`, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(employeeSalary: IEmployeeSalary): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(employeeSalary);
        return this.http
            .put<IEmployeeSalary>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEmployeeSalary>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEmployeeSalary[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    getSubunitsInHrm(): any {
        return this.http.get<any>(`${this.subUnitsInHrmUrl}`, { observe: 'response' });
    }

    getEmployeesFromSelectedDepartment(departmentId: number): any {
        return this.http.get<any>(`${this.employeesInDepartmentUrl}/${departmentId}`, { observe: 'response' });
    }

    protected convertDateFromClient(employeeSalary: IEmployeeSalary): IEmployeeSalary {
        const copy: IEmployeeSalary = Object.assign({}, employeeSalary, {
            createdAt:
                employeeSalary.createdAt != null && employeeSalary.createdAt.isValid()
                    ? employeeSalary.createdAt.format(DATE_FORMAT)
                    : null,
            updatedAt:
                employeeSalary.updatedAt != null && employeeSalary.updatedAt.isValid() ? employeeSalary.updatedAt.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
            res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((employeeSalary: IEmployeeSalary) => {
                employeeSalary.createdAt = employeeSalary.createdAt != null ? moment(employeeSalary.createdAt) : null;
                employeeSalary.updatedAt = employeeSalary.updatedAt != null ? moment(employeeSalary.updatedAt) : null;
            });
        }
        return res;
    }
}
