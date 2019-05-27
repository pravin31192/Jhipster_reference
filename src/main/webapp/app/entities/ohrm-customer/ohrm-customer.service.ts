import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOhrmCustomer } from 'app/shared/model/ohrm-customer.model';

type EntityResponseType = HttpResponse<IOhrmCustomer>;
type EntityArrayResponseType = HttpResponse<IOhrmCustomer[]>;

@Injectable({ providedIn: 'root' })
export class OhrmCustomerService {
    public resourceUrl = SERVER_API_URL + 'api/ohrm-customers';

    constructor(protected http: HttpClient) {}

    create(ohrmCustomer: IOhrmCustomer): Observable<EntityResponseType> {
        return this.http.post<IOhrmCustomer>(this.resourceUrl, ohrmCustomer, { observe: 'response' });
    }

    update(ohrmCustomer: IOhrmCustomer): Observable<EntityResponseType> {
        return this.http.put<IOhrmCustomer>(this.resourceUrl, ohrmCustomer, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOhrmCustomer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOhrmCustomer[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
