import { UserDto } from "../user/userDto";

export interface ComplaintDto {
   

    serialNumber: string;
    qmsNumber: string;
    customerRefNumber: string;
    claimedFault: string;
    arrivedAt: string;
   
    //responsible: string;

    analysisStartedAt: string;
    analysisEndedAt: string;

   // productGroup: string;
    analyzedBy: string;
}