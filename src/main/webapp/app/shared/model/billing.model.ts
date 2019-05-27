import { Moment } from 'moment';

export interface IBilling {
    id?: number;
    clientId?: number;
    project?: number;
    resource?: number;
    hoursAllocated?: number;
    billRate?: number;
    salary?: number;
    otherCost?: number;
    projectName?: string;
    resourceName?: string;
    clientName?: string;
    createdAt?: Moment;
    updatedAt?: Moment;
}

export class Billing implements IBilling {
    constructor(
        public id?: number,
        public clientId?: number,
        public project?: number,
        public resource?: number,
        public hoursAllocated?: number,
        public billRate?: number,
        public salary?: number,
        public otherCost?: number,
        public projectName?: string,
        public resourceName?: string,
        public clientName?: string,
        public createdAt?: Moment,
        public updatedAt?: Moment
    ) {}
}
