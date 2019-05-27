import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProject } from 'app/shared/model/project.model';
import { ProjectSearch } from './project.component';

type EntityResponseType = HttpResponse<IProject>;
type EntityArrayResponseType = HttpResponse<IProject[]>;

@Injectable({ providedIn: 'root' })
export class ProjectService {
    public resourceUrl = SERVER_API_URL + 'api/projects';
    public getSunHrmProjectsUrl = SERVER_API_URL + 'api/sunhrm-projects';
    public getSunHrmClientsUrl = SERVER_API_URL + 'api/sunhrm-clients';
    public getProjectsOfClientsUrl = SERVER_API_URL + 'api/projects-of-clients';
    public searchProjectUrl = SERVER_API_URL + 'api/project-search';

    constructor(protected http: HttpClient) {}

    create(project: IProject): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(project);
        return this.http
            .post<IProject>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(project: IProject): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(project);
        return this.http
            .put<IProject>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProject>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProject[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    getProjectsFromSunHrm() {
        return this.http.get<any>(`${this.getSunHrmProjectsUrl}`, { observe: 'response'});
    }
    getClientsFromSunHrm() {
        return this.http.get<any>(`${this.getSunHrmClientsUrl}`, { observe: 'response'});
    }

    getProjectsOfSelectedClient(clientId: number) {
        return this.http.get<any>(`${this.getProjectsOfClientsUrl}/${clientId}`, { observe: 'response'});
    }

    searchInProjectTable(projectSearch: ProjectSearch) {
        return this.http.post<any>(this.searchProjectUrl, projectSearch, {observe: 'response'});
    }

    protected convertDateFromClient(project: IProject): IProject {
        const copy: IProject = Object.assign({}, project, {
            startDate: project.startDate != null && project.startDate.isValid() ? project.startDate.format(DATE_FORMAT) : null,
            endDate: project.endDate != null && project.endDate.isValid() ? project.endDate.format(DATE_FORMAT) : null,
            createdAt: project.createdAt != null && project.createdAt.isValid() ? project.createdAt.format(DATE_FORMAT) : null,
            updatedAt: project.updatedAt != null && project.updatedAt.isValid() ? project.updatedAt.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
            res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
            res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((project: IProject) => {
                project.startDate = project.startDate != null ? moment(project.startDate) : null;
                project.endDate = project.endDate != null ? moment(project.endDate) : null;
                project.createdAt = project.createdAt != null ? moment(project.createdAt) : null;
                project.updatedAt = project.updatedAt != null ? moment(project.updatedAt) : null;
            });
        }
        return res;
    }
}
