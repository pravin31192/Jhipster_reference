import { Moment } from 'moment';

export interface IRoleUserMapping {
    id?: number;
    userId?: number;
    roleId?: number;
    userName?: string;
    roleName?: string;
    createdAt?: Moment;
    updatedAt?: Moment;
    createdBy?: number;
}

export class RoleUserMapping implements IRoleUserMapping {
    constructor(
        public id?: number,
        public userId?: number,
        public roleId?: number,
        public userName?: string,
        public roleName?: string,
        public createdAt?: Moment,
        public updatedAt?: Moment,
        public createdBy?: number
    ) {}
}
