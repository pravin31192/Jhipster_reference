import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import 'chart.js';

import { LoginModalService, AccountService, Account, LoginService, StateStorageService } from 'app/core';
import { BaseChartDirective } from 'ng2-charts';
import { MainService } from 'app/layouts/main/main.service';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {

    // For Login
    authenticationError: boolean;
    password: string;
    rememberMe: boolean;
    username: string;
    credentials: any;
    account: Account;
    modalRef: NgbModalRef;
    public doughnutChartType = 'doughnut';
    // Pie chart variables
    public pieChartLabels: string[] = [];
    public pieChartData: number[] =  [];
    public pieChartType = 'pie';
    resourceCountArray: any[];
    @ViewChild('baseChart') chart: BaseChartDirective;

    constructor(
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private mainService: MainService,
        private loginService: LoginService,
        private router: Router,
        private stateStorageService: StateStorageService
    ) {
        this.mainService.componentMethodCalled$.subscribe(
            () => {
                this.ngOnInit();
            }
        );
    }

    ngOnInit() {
        this.pieChartLabels.push();
        this.pieChartData.push();
        this.accountService.identity().then(account => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        if (this.isAuthenticated) {
            if (this.pieChartData.length === 0) {
                this.getResourceCountOnProjectForPieChart();
            }
        }
    }

    getResourceCountOnProjectForPieChart() {
        this.accountService.getResourceCountOnProjectForPieChart().subscribe(
            (result: any) => {
                this.resourceCountArray = result.body;
                this.resourceCountArray.forEach( (oneProjectReport, index) => {
                    this.pieChartLabels.push(oneProjectReport.projectName);
                    this.pieChartData.push(oneProjectReport.resourceCount);
                    if (this.chart !== undefined) {
                        this.chart.ngOnDestroy();
                        this.chart.chart = this.chart.getChartBuilder(this.chart.ctx);
                    }
                    // this.reloadChart();
                });
            }
        );
    }

    // reloadChart() {
    //     if (this.chart !== undefined) {
    //        this.chart.ngOnDestroy();
    //        this.chart.chart = 0;
    //        this.chart.datasets = this.pieChartData;
    //        this.chart.labels = this.pieChartLabels;
    //        // this.chart.ngOnInit();
    //     }
    // }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.accountService.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    // login() {
    //     this.modalRef = this.loginModalService.open();
    // }

    // events
    public chartClicked(e: any): void {
        console.log(e);
    }

    public chartHovered(e: any): void {
        console.log(e);
    }

    /**
     * To send validate the username and password entered by the user.
     */
    login() {
        this.loginService
            .login({
                username: this.username,
                password: this.password,
                rememberMe: this.rememberMe
            })
            .then(() => {
                this.authenticationError = false;
                if (this.router.url === '/register' || /^\/activate\//.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
                    this.loginService.callMainServiceFunctionToRefreshTheMainComponent();
                    this.router.navigate(['']);
                }
                this.eventManager.broadcast({
                    name: 'authenticationSuccess',
                    content: 'Sending Authentication Success'
                });

                // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // since login is successful, go to stored previousState and clear previousState
                const redirect = this.stateStorageService.getUrl();
                if (redirect) {
                    this.stateStorageService.storeUrl(null);
                    this.router.navigate([redirect]);
                }
            })
            .catch(() => {
                this.authenticationError = true;
            });
    }
}
