export interface IOhrmCustomer {
    id?: number;
    customerId?: number;
    name?: string;
    description?: string;
    isDeleted?: number;
}

export class OhrmCustomer implements IOhrmCustomer {
    constructor(
        public id?: number,
        public customerId?: number,
        public name?: string,
        public description?: string,
        public isDeleted?: number
    ) {}
}
