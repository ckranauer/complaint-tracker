import { Complaint } from "../complaint/complaint";



export interface ComplaintResponse {
    timeStamp: Date;
    statusCode: number;
    status: string;
    reason: string;
    message: string;
    developerMessage: string;
    data: { complaints?: Complaint[], complaint?: Complaint}
}