import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IBilling } from 'app/shared/model/billing.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { BillingService } from './billing.service';

export class ResourceSearchForm {
    empName: string;
    projectName: string;
    clientName: string;
    // projectType: number;
}

export class ResourceSearch {
    empName: string;
    projectName: string;
    clientName: string;
    // projectType: number;
}

@Component({
    selector: 'jhi-billing',
    templateUrl: './billing.component.html'
})
export class BillingComponent implements OnInit, OnDestroy {
    currentAccount: any;
    billings: IBilling[];
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
    resourceSearchObject: ResourceSearch;
    projectListOfSize: any;

    constructor(
        protected billingService: BillingService,
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

    loadAll() {
        this.billingService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IBilling[]>) => this.paginateBillings(res.body, res.headers),
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
        this.router.navigate(['/billing'], {
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
            '/billing',
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
        this.registerChangeInBillings();
        this.resourceSearch = new ResourceSearch();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBilling) {
        return item.id;
    }

    registerChangeInBillings() {
        this.eventSubscriber = this.eventManager.subscribe('billingListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateBillings(data: IBilling[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.billings = data;
        this.projectListOfSize = this.queryCount;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    search() {
        this.resourceSearchObject = new ResourceSearch();
        this.resourceSearchObject.empName = this.resourceSearch.empName;
        this.resourceSearchObject.projectName = this.resourceSearch.projectName;
        this.resourceSearchObject.clientName = this.resourceSearch.clientName;
        // this.projectSearchObject.projectType = this.projectSearch.projectType;

        this.billingService.searchInResourceTable(this.resourceSearchObject).subscribe((result: any) => {
            this.billings = result.body;
            this.projectListOfSize = this.billings.length;
        });
    }
}
