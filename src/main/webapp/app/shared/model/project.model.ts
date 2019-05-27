import { Moment } from 'moment';

export interface IProject {
    id?: number;
    projectName?: string;
    hrmProjectId?: number;
    clientName?: string;
    hrmClientId?: number;
    clientCode?: string;
    projectCode?: string;
    billableValue?: number;
    noOfResources?: number;
    createdBy?: number;
    status?: number;
    type?: number;
    startDate?: Moment;
    endDate?: Moment;
    estimatedStaffHours?: number;
    actualStaffHours?: number;
    percentageComplete?: number;
    details?: string;
    deliverables?: string;
    attachments?: string;
    createdAt?: Moment;
    updatedAt?: Moment;
}

export class Project implements IProject {
    constructor(
        public id?: number,
        public projectName?: string,
        public hrmProjectId?: number,
        public clientName?: string,
        public hrmClientId?: number,
        public clientCode?: string,
        public projectCode?: string,
        public billableValue?: number,
        public noOfResources?: number,
        public createdBy?: number,
        public status?: number,
        public type?: number,
        public startDate?: Moment,
        public endDate?: Moment,
        public estimatedStaffHours?: number,
        public actualStaffHours?: number,
        public percentageComplete?: number,
        public details?: string,
        public deliverables?: string,
        public attachments?: string,
        public createdAt?: Moment,
        public updatedAt?: Moment
    ) {}
}
