import { Moment } from 'moment';

export interface IEmployeeSalary {
    id?: number;
    employeeId?: number;
    employeeName?: String;
    departmentName?: String;
    departmentId?: number;
    salary?: number;
    createdAt?: Moment;
    updatedAt?: Moment;
    createdBy?: number;
}

export class EmployeeSalary implements IEmployeeSalary {
    constructor(
        public id?: number,
        public employeeId?: number,
        public employeeName?: String,
        public departmentName?: String,
        public departmentId?: number,
        public salary?: number,
        public createdAt?: Moment,
        public updatedAt?: Moment,
        public createdBy?: number
    ) {}
}
