
export interface ComplaintCreateDto {
   

    serialNumber: string;
    qmsNumber: string;
    customerReference: string;
    claimedFault: string;
    arrivedAt: Date;
    isPrio: boolean;
    responsible: number;
}