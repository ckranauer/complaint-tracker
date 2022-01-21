
export interface ComplaintCreateDto {
   

    serialNumber: string;
    qmsNumber: string;
    customerRefNumber: string;
    claimedFault: string;
    arrivedAt: Date;
    isPrio: boolean;
    responsible: number;
}

