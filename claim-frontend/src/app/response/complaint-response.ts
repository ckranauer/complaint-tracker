import { Complaint } from "../complaint/complaint";
import { ComplaintDto } from "../complaint/complaintDto";



export interface ComplaintResponse {
    timeStamp: Date;
    statusCode: number;
    status: string;
    reason: string;
    message: string;
    developerMessage: string;
    data: { complaints?: ComplaintDto[], complaint?: ComplaintDto}
}