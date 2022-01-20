import { UserDto } from "../user/userDto";

export interface ComplaintDto {
   

    serial_number: string;
    qms_number: string;
    customer_ref_number: string;
    claimed_fault: string;
    arrived_at: string;
   
    //responsible: string;

    analysis_started_at: string;
    analysis_ended_at: string;

   // productGroup: string;
    analyzed_by: string;
}