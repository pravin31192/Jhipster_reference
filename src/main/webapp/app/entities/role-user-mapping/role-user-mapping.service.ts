import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRoleUserMapping } from 'app/shared/model/role-user-mapping.model';

type EntityResponseType = HttpResponse<IRoleUserMapping>;
type EntityArrayResponseType = HttpResponse<IRoleUserMapping[]>;

@Injectable({ providedIn: 'root' })
export class RoleUserMappingService {
    public resourceUrl = SERVER_API_URL + 'api/role-user-mappings';
    private getRolesUrl = SERVER_API_URL + 'api/roles';

    constructor(protected http: HttpClient) {}

    create(roleUserMapping: IRoleUserMapping): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(roleUserMapping);
        return this.http
            .post<IRoleUserMapping>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(roleUserMapping: IRoleUserMapping): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(roleUserMapping);
        return this.http
            .put<IRoleUserMapping>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRoleUserMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRoleUserMapping[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    loadRolesInPrms() {
        return this.http.get<any>(`${this.getRolesUrl}`, { observe: 'response'});
           // .pipe(map((res: EntityResponseType) => this.convertResponse(res)));
    }

    protected convertDateFromClient(roleUserMapping: IRoleUserMapping): IRoleUserMapping {
        const copy: IRoleUserMapping = Object.assign({}, roleUserMapping, {
            createdAt:
                roleUserMapping.createdAt != null && roleUserMapping.createdAt.isValid()
                    ? roleUserMapping.createdAt.format(DATE_FORMAT)
                    : null,
            updatedAt:
                roleUserMapping.updatedAt != null && roleUserMapping.updatedAt.isValid()
                    ? roleUserMapping.updatedAt.format(DATE_FORMAT)
                    : null
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
            res.body.forEach((roleUserMapping: IRoleUserMapping) => {
                roleUserMapping.createdAt = roleUserMapping.createdAt != null ? moment(roleUserMapping.createdAt) : null;
                roleUserMapping.updatedAt = roleUserMapping.updatedAt != null ? moment(roleUserMapping.updatedAt) : null;
            });
        }
        return res;
    }
}
