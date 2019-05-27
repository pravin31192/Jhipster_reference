import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { VERSION } from 'app/app.constants';
import { AccountService, LoginModalService, LoginService, Account } from 'app/core';
import { ProfileService } from '../profiles/profile.service';
import { MainService } from '../main/main.service';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['navbar.scss']
})
export class NavbarComponent implements OnInit {
    account: Account;
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    menuArray: any[] = [];

    constructor(
        private loginService: LoginService,
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private router: Router,
        private mainService: MainService
    ) {

        this.mainService.componentMethodCalled$.subscribe(
            () => {
                this.ngOnInit();
            }
        );

        this.menuArray = [];
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
        this.accountService.identity().then(account => {
            this.account = account;
            console.log(this.account.authorities);
            this.determineTheRoleBasedMenu(this.account);
        });
    }

    ngOnInit() {
        console.log('Loading Process -  ng On Init in Nav bar component.ts');
        this.menuArray = [];
        this.profileService.getProfileInfo().then(profileInfo => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
            this.accountService.identity().then(account => {
                this.account = account;
                console.log(this.account.authorities);
                this.determineTheRoleBasedMenu(this.account);
            });
        });
    }

    ngOnChange() {
        this.accountService.identity().then(account => {
            console.log('Account has to be checked');
            this.account = account;
            console.log(this.account.authorities);
            this.determineTheRoleBasedMenu(this.account);
        });
    }

    determineTheRoleBasedMenu(userDetails: Account) {
        this.menuArray = [];
        console.log(userDetails.authorities);
        if (userDetails.authorities[0] === 'ROLE_ADMIN') {
            console.log('Role admin if else');
            this.getAdminRoleMenuItems();
        } else if (userDetails.authorities[0] === 'ROLE_PROJECT_MANAGER') {
            console.log('Role Project manager if else');
            this.getProjectManagerMenuItems();
        } else if (userDetails.authorities[0] === 'ROLE_SALES_MANAGER') {
            console.log('Role sales manager if else');
            this.getSalesManagerMenu();
        } else if (userDetails.authorities[0] === 'ROLE_HR_MANAGER') {
            console.log('hr manager if else');
            this.getHrManagerMenu();
        } else if (userDetails.authorities[0] === 'ROLE_FINANCIAL_ANALYST') {
            console.log('financial analyst if else');
            this.getFinanceAnalystMenu();
        }
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.accountService.getImageUrl() : null;
    }

    getAdminRoleMenuItems() {
        this.menuArray[0] = {
            name: 'Project',
            routerLink: 'project',
        };
        this.menuArray[1] = {
            name: 'Resources',
            routerLink: 'project-resource-assign',
        };
        this.menuArray[2] = {
            name: 'Billing',
            routerLink: 'billing',
        };
        this.menuArray[3] = {
            name: 'Settings',
            routerLink: '',
            child: [
                {
                    name: 'Roles',
                    routerLink: 'roles',
                },
                {
                    name: 'Assign Roles',
                    routerLink: 'role-user-mapping',
                },
                {
                    name: 'Employee Salary',
                    routerLink: 'employee-salary',
                },
            ]
        };
        this.menuArray[4] = {
            name: 'Reports',
            routerLink: '',
            child: [
                {
                    name: 'View Resource By Client',
                    routerLink: 'resource-report-by-client',
                },
                {
                    name: 'View Resource By Project',
                    routerLink: 'resource-report-by-project',
                },
                {
                    name: 'View Resource By Percentage Available',
                    routerLink: 'resource-report-by-percentage',
                },
                {
                    name: 'View Resource By Department',
                    routerLink: 'resource-by-department',
                },
                {
                    name: 'Sun Technologies Resource Strength',
                    routerLink: 'suntech-resource-strength',
                },
                {
                    name: 'Billing Report',
                    routerLink: 'billing/report',
                },
            ]
        };
        return this.menuArray;
    }

    getProjectManagerMenuItems() {
        this.menuArray[0] = {
            name: 'Resources',
            routerLink: 'project-resource-assign',
        };
        this.menuArray[1] = {
            name: 'Reports',
            routerLink: '',
            child: [
                {
                    name: 'View Resource By Client',
                    routerLink: 'resource-report-by-client',
                },
                {
                    name: 'View Resource By Project',
                    routerLink: 'resource-report-by-project',
                },
                {
                    name: 'View Resource By Percentage Available',
                    routerLink: 'resource-report-by-percentage',
                },
            ]
        };
         return this.menuArray;
    }

    getSalesManagerMenu() {
        this.menuArray[0] = {
            name: 'Project',
            routerLink: 'project',
        };
        this.menuArray[1] = {
            name: 'Billing',
            routerLink: 'billing',
        };
        this.menuArray[2] = {
            name: 'Reports',
            routerLink: '',
            child: [
                {
                    name: 'View Resource By Client',
                    routerLink: 'resource-report-by-client',
                },
                {
                    name: 'View Resource By Project',
                    routerLink: 'resource-report-by-project',
                },
                {
                    name: 'View Resource By Percentage Available',
                    routerLink: 'resource-report-by-percentage',
                },
                {
                    name: 'View Resource By Department',
                    routerLink: 'resource-by-department',
                },
                {
                    name: 'Sun Technologies Resource Strength',
                    routerLink: 'suntech-resource-strength',
                },
            ]
        };
        return this.menuArray;
    }

    getHrManagerMenu() {
        this.menuArray[0] = {
            name: 'Reports',
            routerLink: '',
            child: [
                {
                    name: 'View Resource By Client',
                    routerLink: 'resource-report-by-client',
                },
                {
                    name: 'View Resource By Project',
                    routerLink: 'resource-report-by-project',
                },
                {
                    name: 'View Resource By Percentage Available',
                    routerLink: 'resource-report-by-percentage',
                },
                {
                    name: 'View Resource By Department',
                    routerLink: 'resource-by-department',
                },
                {
                    name: 'Sun Technologies Resource Strength',
                    routerLink: 'suntech-resource-strength',
                },
            ]
        };
        this.menuArray[1] = {
            name: 'Settings',
            routerLink: '',
            child: [
                {
                    name: 'Employee Salary',
                    routerLink: 'employee-salary',
                },
            ]
        };
    }

    getFinanceAnalystMenu() {
        this.menuArray[0] = {
            name: 'Billing',
            routerLink: 'billing',
        };
        this.menuArray[1] = {
            name: 'Reports',
            routerLink: '',
            child: [
                {
                    name: 'View Resource By Client',
                    routerLink: 'resource-report-by-client',
                },
                {
                    name: 'View Resource By Project',
                    routerLink: 'resource-report-by-project',
                },
                {
                    name: 'View Resource By Percentage Available',
                    routerLink: 'resource-report-by-percentage',
                },
                {
                    name: 'View Resource By Department',
                    routerLink: 'resource-by-department',
                },
                {
                    name: 'Sun Technologies Resource Strength',
                    routerLink: 'suntech-resource-strength',
                },
            ]
        };
        this.menuArray[2] = {
            name: 'Settings',
            routerLink: '',
            child: [
                {
                    name: 'Employee Salary',
                    routerLink: 'employee-salary',
                },
            ]
        };
        return this.menuArray;
    }
}
