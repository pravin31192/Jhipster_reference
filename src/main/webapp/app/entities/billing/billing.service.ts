import { Injectable, ViewContainerRef } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBilling } from 'app/shared/model/billing.model';
import { ResourceSearch } from './billing.component';
// import { ToastsManager, Toast } from 'ng2-toastr/ng2-toastr';

type EntityResponseType = HttpResponse<IBilling>;
type EntityArrayResponseType = HttpResponse<IBilling[]>;

@Injectable({ providedIn: 'root' })
export class BillingService {
    public resourceUrl = SERVER_API_URL + 'api/billings';
    private getProjectsUrl = SERVER_API_URL + 'api/projects';
    private getProjectResourcesUrl = SERVER_API_URL + 'api/get-resources-of-selected-project';
    private getHourOfResourceUrl = SERVER_API_URL + 'api/get-hours-of-resource';
    private getSalaryOfResourceUrl = SERVER_API_URL + 'api/get-salary-of-resource';
    public getSunHrmClientsUrl = SERVER_API_URL + 'api/sunhrm-clients';
    public getProjectsOfClientsUrl = SERVER_API_URL + 'api/projects-of-clients';
    public getInitialBillingReportUrl = SERVER_API_URL + 'api/initial-billing-report';
    public getBillValueResourceUrl = SERVER_API_URL + 'api/billvalue-of-resource-project';
    public searchProjectResourceUrl = SERVER_API_URL + 'api/billing-search';

    constructor(protected http: HttpClient) // public toaster: ToastsManager,
    // vcr: ViewContainerRef
    {
        // this.toaster.setRootViewContainerRef(vcr);
    }

    create(billing: IBilling): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(billing);
        return this.http
            .post<IBilling>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(billing: IBilling): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(billing);
        return this.http
            .put<IBilling>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBilling[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    loadProjectsInPrms() {
        // this.toaster.success('Projects Loaded','Projects are loaded in the dropdown');
        return this.http.get<any>(`${this.getProjectsUrl}`, { observe: 'response' });
    }

    geResourcesOfSelectedProject(projectId: number) {
        return this.http.get<any>(`${this.getProjectResourcesUrl}/${projectId}`, { observe: 'response' });
    }

    getHoursOfResource(selectedProject: number, selectedResource: number) {
        return this.http.get<any>(`${this.getHourOfResourceUrl}/${selectedProject}/${selectedResource}`, { observe: 'response' });
    }

    getSalaryOfResource(selectedResource: number) {
        return this.http.get<any>(`${this.getSalaryOfResourceUrl}/${selectedResource}`, { observe: 'response' });
    }

    getClientsFromSunHrm() {
        return this.http.get<any>(`${this.getSunHrmClientsUrl}`, { observe: 'response' });
    }

    getProjectsOfSelectedClient(clientId: number) {
        return this.http.get<any>(`${this.getProjectsOfClientsUrl}/${clientId}`, { observe: 'response' });
    }

    getInitialBillingReport() {
        return this.http.get<any>(`${this.getInitialBillingReportUrl}`, { observe: 'response' });
    }

    getBillValueOfResource(project, resource) {
        return this.http.get<any>(`${this.getBillValueResourceUrl}/${project}/${resource}`, { observe: 'response' });
    }

    searchInResourceTable(resourceSearch: ResourceSearch) {
        return this.http.post<any>(this.searchProjectResourceUrl, resourceSearch, { observe: 'response' });
    }

    protected convertDateFromClient(billing: IBilling): IBilling {
        const copy: IBilling = Object.assign({}, billing, {
            createdAt: billing.createdAt != null && billing.createdAt.isValid() ? billing.createdAt.format(DATE_FORMAT) : null,
            updatedAt: billing.updatedAt != null && billing.updatedAt.isValid() ? billing.updatedAt.format(DATE_FORMAT) : null
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
            res.body.forEach((billing: IBilling) => {
                billing.createdAt = billing.createdAt != null ? moment(billing.createdAt) : null;
                billing.updatedAt = billing.updatedAt != null ? moment(billing.updatedAt) : null;
            });
        }
        return res;
    }
}
