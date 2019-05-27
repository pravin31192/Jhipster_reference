import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IProjectResourceAssign } from 'app/shared/model/project-resource-assign.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ProjectResourceAssignService } from './project-resource-assign.service';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { Observable } from 'rxjs';

export class ResourceSearchForm {
    empName: string;
    projectName: string;
    clientName: string;
    // projectType: number;
}

export class ResourceSearchObject {
    empName: string;
    projectName: string;
    clientName: string;
    // projectType: number;
}

@Component({
    selector: 'jhi-project-resource-assign',
    templateUrl: './project-resource-assign.component.html'
})
export class ProjectResourceAssignComponent implements OnInit, OnDestroy {
    currentAccount: any;
    projectResourceAssigns: IProjectResourceAssign[];
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
    resourceSearch: ResourceSearchForm;
    resourceSearchObject: ResourceSearchObject;
    projectListOfSize: any;
    model: any;
    searchProjects: any[] = [];
    tempProjectList: any[];
    searchResources: any[] = [];
    tempResourceList: any[];

    constructor(
        protected projectResourceAssignService: ProjectResourceAssignService,
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

    searchProjectsHtml = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            distinctUntilChanged(),
            map(
                term => (
                    term.length < 2 ? [] : this.searchProjects.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)
                    )
            )
    );

    searchResourcesHtml = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            distinctUntilChanged(),
            map(
                term => (
                    term.length < 2 ? [] : this.searchResources.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)
                    )
            )
    );

    loadAll() {
        this.projectResourceAssignService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IProjectResourceAssign[]>) => this.paginateProjectResourceAssigns(res.body, res.headers),
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
        this.router.navigate(['/project-resource-assign'], {
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
            '/project-resource-assign',
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
        this.registerChangeInProjectResourceAssigns();
        this.resourceSearch = new ResourceSearchForm();
        this.loadProjectsInHrm();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProjectResourceAssign) {
        return item.id;
    }

    registerChangeInProjectResourceAssigns() {
        this.eventSubscriber = this.eventManager.subscribe('projectResourceAssignListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateProjectResourceAssigns(data: IProjectResourceAssign[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.projectResourceAssigns = data;
        this.projectListOfSize = this.queryCount;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    /**
     * To get the name of the type of the resource billing.
     * @param billingType Integer value of billing type.
     */
    getBillingTypeName(billingType: number) {
        let billingTypeName = '';
        switch (billingType) {
            case 1:
                billingTypeName = 'Fixed';
                break;
            case 2:
                billingTypeName = 'Time and Material';
                break;
        }
        return billingTypeName;
    }

    loadProjectsInHrm() {
        this.projectResourceAssignService.loadProjectsInPrms().subscribe(
            result => {
                this.tempProjectList = result.body;
                this.tempProjectList.forEach(oneProject => {
                    this.searchProjects.push(oneProject.projectName);
                });
            }
        ); 
    }

    loadResourcesOfProject(selectedProject) {
        let selectedProjectString = selectedProject.item;
        console.log(selectedProjectString);
        let result = this.tempProjectList.find(x => x.projectName === selectedProjectString);
        let projectId = result.id;
        console.log(projectId);
        this.loadResourcesOfProjectOnSelect(projectId);
    }

    loadResourcesOfProjectOnSelect(projectId) {
        this.projectResourceAssignService.geResourcesOfSelectedProject(projectId).subscribe(
            result => {
                this.tempResourceList = result.body;
                this.tempResourceList.forEach(oneProject => {
                    this.searchResources.push(oneProject.empName);
                });
            }
        );
    }

    search() {
        this.resourceSearchObject = new ResourceSearchObject();
        this.resourceSearchObject.empName = this.resourceSearch.empName;
        this.resourceSearchObject.projectName = this.resourceSearch.projectName;
        this.resourceSearchObject.clientName = this.resourceSearch.clientName;
        // this.projectSearchObject.projectType = this.projectSearch.projectType;

        this.projectResourceAssignService.searchInResourceTable(this.resourceSearchObject).subscribe((result: any) => {
            this.projectResourceAssigns = result.body;
            this.projectListOfSize = this.projectResourceAssigns.length;
        });
    }

    getResourceStatusInString(statusInInteger) {
        if (statusInInteger === 1) {
            return 'ACTIVE';
        } else {
            return 'INACTIVE';
        }
    }
}
