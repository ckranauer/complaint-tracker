import { PrinterServerDto } from "../printer-server/dtos/printerServerDto";


export interface PrinterServerResponse {
    timeStamp: Date;
    statusCode: number;
    status: string;
    reason: string;
    message: string;
    developerMessage: string;
    data: { servers?: PrinterServerDto[], server?: PrinterServerDto}
}