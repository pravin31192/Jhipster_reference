import { Moment } from 'moment';

export interface IProjectResourceAssign {
    id?: number;
    projectId?: number;
    empId?: number;
    role?: number;
    fromDate?: Moment;
    toDate?: Moment;
    billingType?: number;
    billValue?: number;
    percentageOfWork?: number;
    projectName?: string;
    empName?: string;
    roleName?: string;
    clientName?: string;
    estimatedStaffHours?: number;
    actualStaffHours?: number;
    location?: number;
    status?: number;
    createdAt?: Moment;
    updatedAt?: Moment;
}

export class ProjectResourceAssign implements IProjectResourceAssign {
    constructor(
        public id?: number,
        public projectId?: number,
        public empId?: number,
        public role?: number,
        public fromDate?: Moment,
        public toDate?: Moment,
        public billingType?: number,
        public billValue?: number,
        public percentageOfWork?: number,
        public projectName?: string,
        public empName?: string,
        public roleName?: string,
        public clientName?: string,
        public estimatedStaffHours?: number,
        public actualStaffHours?: number,
        public location?: number,
        public status?: number,
        public createdAt?: Moment,
        public updatedAt?: Moment
    ) {}
}
