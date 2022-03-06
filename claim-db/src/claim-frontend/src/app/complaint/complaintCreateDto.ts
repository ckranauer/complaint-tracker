
export interface ComplaintCreateDto {
   

    serialNumber: string;
    qmsNumber: string;
    customerRefNumber: string;
    productInfo: string;
    claimedFault: string;
    arrivedAt: Date;
    isPrio: boolean;
    responsible: string;
}

