import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRoles } from 'app/shared/model/roles.model';

type EntityResponseType = HttpResponse<IRoles>;
type EntityArrayResponseType = HttpResponse<IRoles[]>;

@Injectable({ providedIn: 'root' })
export class RolesService {
    public resourceUrl = SERVER_API_URL + 'api/roles';

    constructor(protected http: HttpClient) {}

    create(roles: IRoles): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(roles);
        return this.http
            .post<IRoles>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(roles: IRoles): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(roles);
        return this.http
            .put<IRoles>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRoles>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRoles[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(roles: IRoles): IRoles {
        const copy: IRoles = Object.assign({}, roles, {
            createdAt: roles.createdAt != null && roles.createdAt.isValid() ? roles.createdAt.format(DATE_FORMAT) : null,
            updatedAt: roles.updatedAt != null && roles.updatedAt.isValid() ? roles.updatedAt.format(DATE_FORMAT) : null
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
            res.body.forEach((roles: IRoles) => {
                roles.createdAt = roles.createdAt != null ? moment(roles.createdAt) : null;
                roles.updatedAt = roles.updatedAt != null ? moment(roles.updatedAt) : null;
            });
        }
        return res;
    }
}
