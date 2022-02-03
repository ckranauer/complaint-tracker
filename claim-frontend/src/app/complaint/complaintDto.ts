import { UserDto } from "../user/userDto";

export interface ComplaintDto {
   
    id: number;
    serialNumber: string;
    qmsNumber: string;
    customerRefNumber: string;
    claimedFault: string;
    arrivedAt: string;
    productInfo: string;
    responsible: string;
    responsibleId: string;

    analysisStartedAt: string;
    analysisEndedAt: string;

    faultVerification: boolean;

    barcodes: string;
    lifecycleInfo: string;
    visualAnalysis: string;
    electricalAnalysis: string;
    conclusion: string;

   // productGroup: string;
    analyzedBy: string;
    analyzedById: string;

    isPrio: boolean;
   
}


