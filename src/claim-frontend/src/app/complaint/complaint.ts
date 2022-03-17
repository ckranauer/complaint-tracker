import { User } from "../user/user";


export interface Complaint {
    id: number;
    serialNumber: string;
    qmsNumber: string;
    customerRefNumber: string;
    claimedFault: string;
    createdAt: Date;
    arrivedAt: Date;
    responsible: User;
    prio: boolean
}