import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IProject } from 'app/shared/model/project.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ProjectService } from './project.service';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { Observable } from 'rxjs';

export class ProjectSearchForm {
    projectName: string;
    clientName: string;
    projectType: number;
    projectStatus: number;
    percentageComplete: number;
}

export class ProjectSearch {
    projectName: string;
    clientName: string;
    projectType: number;
    projectStatus: number;
    percentageComplete: number;
}

@Component({
    selector: 'jhi-project',
    templateUrl: './project.component.html'
})
export class ProjectComponent implements OnInit, OnDestroy {
    currentAccount: any;
    projects: IProject[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    projectSearch: ProjectSearchForm;
    projectSearchObject: ProjectSearch;
    projectListOfSize: any;
    model: any;
    clients:any[] = [];
    localClients: any[];
    hrmProjects: any[] = [];
    localHrmProjects: any[];
    
    constructor(
        protected projectService: ProjectService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    searchClient = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            distinctUntilChanged(),
            map(
                term => (
                    term.length < 2 ? [] : this.clients.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)
                    )
            )
    );

    searchProjects = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            distinctUntilChanged(),
            map(
                term => (
                    term.length < 2 ? [] : this.hrmProjects.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)
                    )
            )
    );

    loadAll() {
        this.projectService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IProject[]>) => this.paginateProjects(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/project'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/project',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProjects();
        this.projectSearch = new ProjectSearch();
        this.loadClientsInSunHrm();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProject) {
        return item.id;
    }

    registerChangeInProjects() {
        this.eventSubscriber = this.eventManager.subscribe('projectListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateProjects(data: IProject[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.projects = data;
        this.projectListOfSize = this.queryCount;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    /**
     * To return the actual status of the project in the string.
     * @param projectStatus status of the project in INTEGER
     */
    getProjectStatusInString(projectStatus: number) {
        let status = '';
        switch (projectStatus) {
            case 1:
                status = 'NEW';
                break;
            case 2:
                status = 'IN PROGRESS';
                break;
            case 3:
                status = 'COMPLETED';
                break;
            case 4:
                status = 'ON HOLD';
                break;
        }
        return status;
    }

    getProjectTypeInString(projectType: number) {
        let type = '';
        switch (projectType) {
            case 1:
                type = 'Fixed';
                break;
            case 2:
                type = 'Time and Material';
                break;
        }
        return type;
    }

    loadClientsInSunHrm() {
        this.projectService.getClientsFromSunHrm().subscribe(
            (result: any) => {
                console.log("Before "+this.clients);
                this.localClients = result.body;
                this.localClients.forEach(oneClient => {
                    this.clients.push(oneClient.name);
                });
            }
        );
    }

    loadProjectsOfClient(selectedClient) {
        let selectedClientString = selectedClient.item;
        console.log(selectedClientString);
        let result = this.localClients.find(x => x.name === selectedClientString);
        let clientId = result.customerId;
        console.log(clientId);
        this.loadProjectsOfSelectedClient(clientId);
    }

    /**
     * To load the projects of the selected client from the dropdown
     */
    loadProjectsOfSelectedClient(selectedClient) {
        this.projectService.getProjectsOfSelectedClient(selectedClient).subscribe(
            (result: any) => {
                this.localHrmProjects = result.body;
                this.localHrmProjects.forEach(oneProject => {
                    this.hrmProjects.push(oneProject.name);
                });
            }
        );
    }

    search() {
        this.projectSearchObject = new ProjectSearch();
        this.projectSearchObject.clientName = this.projectSearch.clientName;
        this.projectSearchObject.projectName = this.projectSearch.projectName;
        this.projectSearchObject.projectType = this.projectSearch.projectType;
        this.projectSearchObject.projectStatus = this.projectSearch.projectStatus;
        this.projectSearchObject.percentageComplete = this.projectSearch.percentageComplete;
        this.projectService.searchInProjectTable(this.projectSearchObject).subscribe((result: any) => {
            this.projects = result.body;
            this.projectListOfSize = this.projects.length;
            this.itemsPerPage = this.projects.length;
            this.transition();
        });
    }
}
