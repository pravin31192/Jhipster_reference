import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProjectResourceAssign } from 'app/shared/model/project-resource-assign.model';
import { Roles } from 'app/shared/model/roles.model';
import { ProjectResourceAssignDTO } from './project-resource-assign-update.component';
import { ResourceSearchObject } from './project-resource-assign.component';

type EntityResponseType = HttpResponse<IProjectResourceAssign>;
type EntityArrayResponseType = HttpResponse<IProjectResourceAssign[]>;

@Injectable({ providedIn: 'root' })
export class ProjectResourceAssignService {
    public resourceUrl = SERVER_API_URL + 'api/project-resource-assigns';
    private getRolesUrl = SERVER_API_URL + 'api/roles';
    private getProjects = SERVER_API_URL + 'api/projects';
    private getResourceOnRole = SERVER_API_URL + 'api/resource-on-role';
    private getProjectResourcesUrl = SERVER_API_URL + 'api/get-resources-of-selected-prms-project';
    private checkRoleResourcesOnProjectUrl = SERVER_API_URL + 'api/role-resource-already-on-project';
    private validatePercentageUrl = SERVER_API_URL + 'api/validate-percentage';
    private resourcesOnAvailabilityUrl = SERVER_API_URL + 'api/resources-on-availability';
    public getSunHrmClientsUrl = SERVER_API_URL + 'api/sunhrm-clients';
    public getResourcesOfClientsUrl = SERVER_API_URL + 'api/resources-of-clients';
    private subUnitsInHrmUrl = SERVER_API_URL + 'api/sub-units-in-hrm';
    private employeesInDepartmentUrl = SERVER_API_URL + 'api/employees-in-department';
    public searchProjectResourceUrl = SERVER_API_URL + 'api/resource-search';
    private getProjectDetailsURL = SERVER_API_URL + 'api/projects';
    private releaseResourceURL = SERVER_API_URL + 'api/release-from-project';
    constructor(protected http: HttpClient) {}

    create(projectResourceAssign: ProjectResourceAssignDTO): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(projectResourceAssign);
        return this.http
            .post<IProjectResourceAssign>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(projectResourceAssign: ProjectResourceAssignDTO): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(projectResourceAssign);
        return this.http
            .put<IProjectResourceAssign>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProjectResourceAssign>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProjectResourceAssign[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    getClientsFromSunHrm() {
        return this.http.get<any>(`${this.getSunHrmClientsUrl}`, { observe: 'response' });
    }

    getSubunitsInHrm(): any {
        return this.http.get<any>(`${this.subUnitsInHrmUrl}`, { observe: 'response' });
    }

    loadRolesInPrms() {
        return this.http.get<any>(`${this.getRolesUrl}`, { observe: 'response' });
        // .pipe(map((res: EntityResponseType) => this.convertResponse(res)));
    }

    loadProjectsInPrms() {
        return this.http.get<any>(`${this.getProjects}`, { observe: 'response' });
    }

    getEmployeesFromSelectedDepartment(departmentId: number): any {
        return this.http.get<any>(`${this.employeesInDepartmentUrl}/${departmentId}`, { observe: 'response' });
    }

    loadResourceBasedOnRole(roleId) {
        return this.http.get<any>(`${this.getResourceOnRole}/${roleId}`, { observe: 'response' });
    }

    checkResourceAlreadyInSelectedProject(selectedResource, selectedProject, selectedRole) {
        return this.http.get<any>(`${this.checkRoleResourcesOnProjectUrl}/${selectedResource}/${selectedProject}/${selectedRole}`, {
            observe: 'response'
        });
    }

    geResourcesOfSelectedProject(projectId: number) {
        return this.http.get<any>(`${this.getProjectResourcesUrl}/${projectId}`, { observe: 'response' });
    }

    validateAvailalbePercentage(enteredPerncentage: number, selectedResource: any): any {
        return this.http.get<any>(`${this.validatePercentageUrl}/${enteredPerncentage}/${selectedResource}`, { observe: 'response' });
    }

    getResourcesOnAvailableRange(selectedAvailableRange) {
        return this.http.get<any>(`${this.resourcesOnAvailabilityUrl}/${selectedAvailableRange}`, { observe: 'response' });
    }

    getResourcesOfClients(clientId) {
        return this.http.get<any>(`${this.getResourcesOfClientsUrl}/${clientId}`, { observe: 'response' });
    }

    searchInResourceTable(resourceSearch: ResourceSearchObject) {
        return this.http.post<any>(this.searchProjectResourceUrl, resourceSearch, { observe: 'response' });
    }

    getStartDateAndEndDate(projectId) {
        return this.http.get<any>(`${this.getProjectDetailsURL}/${projectId}`, { observe: 'response' });
    }

    releaseResourceFromProject(assignedId) {
        console.log("Service in resouerce assign " + assignedId);
        return this.http.get<any>(`${this.releaseResourceURL}/${assignedId}`, { observe: 'response' });
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Roles = this.convertItemFromServer(res.body);
        return res.clone({ body });
    }
    /**
     * Convert a returned JSON object to Billing.
     */
    private convertItemFromServer(billing: Roles): Roles {
        const copy: Roles = Object.assign({}, billing);
        return copy;
    }

    protected convertDateFromClient(projectResourceAssign: IProjectResourceAssign): IProjectResourceAssign {
        const copy: IProjectResourceAssign = Object.assign({}, projectResourceAssign, {
            fromDate:
                projectResourceAssign.fromDate != null && projectResourceAssign.fromDate.isValid()
                    ? projectResourceAssign.fromDate.format(DATE_FORMAT)
                    : null,
            toDate:
                projectResourceAssign.toDate != null && projectResourceAssign.toDate.isValid()
                    ? projectResourceAssign.toDate.format(DATE_FORMAT)
                    : null,
            createdAt:
                projectResourceAssign.createdAt != null && projectResourceAssign.createdAt.isValid()
                    ? projectResourceAssign.createdAt.format(DATE_FORMAT)
                    : null,
            updatedAt:
                projectResourceAssign.updatedAt != null && projectResourceAssign.updatedAt.isValid()
                    ? projectResourceAssign.updatedAt.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fromDate = res.body.fromDate != null ? moment(res.body.fromDate) : null;
            res.body.toDate = res.body.toDate != null ? moment(res.body.toDate) : null;
            res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
            res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((projectResourceAssign: IProjectResourceAssign) => {
                projectResourceAssign.fromDate = projectResourceAssign.fromDate != null ? moment(projectResourceAssign.fromDate) : null;
                projectResourceAssign.toDate = projectResourceAssign.toDate != null ? moment(projectResourceAssign.toDate) : null;
                projectResourceAssign.createdAt = projectResourceAssign.createdAt != null ? moment(projectResourceAssign.createdAt) : null;
                projectResourceAssign.updatedAt = projectResourceAssign.updatedAt != null ? moment(projectResourceAssign.updatedAt) : null;
            });
        }
        return res;
    }
}
