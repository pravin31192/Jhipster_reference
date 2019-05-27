import { Moment } from 'moment';

export interface IRoles {
    id?: number;
    name?: string;
    description?: string;
    createdAt?: Moment;
    updatedAt?: Moment;
}

export class Roles implements IRoles {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public createdAt?: Moment,
        public updatedAt?: Moment
    ) {}
}
