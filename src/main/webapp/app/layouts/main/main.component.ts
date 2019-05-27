import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd } from '@angular/router';

import { Title } from '@angular/platform-browser';
import { MainService } from './main.service';

@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html'
})
export class JhiMainComponent implements OnInit {
    constructor(private titleService: Title, private router: Router) {}

    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : 'prmsApp';
        if (routeSnapshot.firstChild) {
            title = this.getPageTitle(routeSnapshot.firstChild) || title;
        }
        return title;
    }

    ngOnInit() {
        console.log('Loading Process - ng On Init in Main component.ts');
        this.router.events.subscribe(event => {
            console.log(' Loading Process -  Main component - inside router events');
            if (event instanceof NavigationEnd) {
                this.titleService.setTitle(this.getPageTitle
                (this.router.routerState.snapshot.root));
            }
        });
    }
}
