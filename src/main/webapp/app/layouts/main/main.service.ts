import { Injectable, ViewContainerRef } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class MainService {
    private componentMethodCallSource = new Subject<any>();
    componentMethodCalled$ = this.componentMethodCallSource.asObservable();
    constructor(protected http: HttpClient) {}

    refreshMainCompomnent() {
        this.componentMethodCallSource.next();
    }
}
