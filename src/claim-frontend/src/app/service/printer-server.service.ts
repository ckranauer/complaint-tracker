import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PaginationObj } from '../interface/pagination-obj';
import { PrinterServerCreateDto } from '../printer-server/dtos/printerServerCreateDto';
import { PrinterServerDto } from '../printer-server/dtos/printerServerDto';
import { PrinterServerResponse } from '../response/printer-server-response';

@Injectable({
  providedIn: 'root'
})
export class PrinterServerService {




  private readonly apiUrl = 'http://complainttracker-env.eba-zqnmujjv.us-east-1.elasticbeanstalk.com';// 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  createServer(server: PrinterServerCreateDto): Observable<PrinterServerResponse> {
    return this.http.post<PrinterServerResponse>(`${this.apiUrl}/printer-server/save`, server)
  }

  getServers(paginationObj: PaginationObj): Observable<PrinterServerResponse> {
    return this.http.post<PrinterServerResponse>(`${this.apiUrl}/printer-server/list`, paginationObj);
  }

  getServer(id :number): Observable<PrinterServerResponse> {
    return this.http.get<PrinterServerResponse>(`${this.apiUrl}/printer-server/get/${id}`);
  }


  updateServer(server: PrinterServerDto): Observable<PrinterServerResponse> {
    return this.http.put<PrinterServerResponse>(`${this.apiUrl}/printer-server/update`, server)
  }

  deleteServer(id: number): Observable<PrinterServerResponse> {
    return this.http.delete<PrinterServerResponse>(`${this.apiUrl}/printer-server/delete/${id}`)
  }



  /*
  createComplaint(complaint: ComplaintCreateDto): Observable<ComplaintResponse> {
    return this.http.post<ComplaintResponse>(`${this.apiUrl}/complaint/save`, complaint)
  }

  getComplaints(paginationObj: PaginationObj): Observable<ComplaintResponse> {
      return this.http.post<ComplaintResponse>(`${this.apiUrl}/complaint/list`, paginationObj);
  }

  getComplaint(id :number): Observable<ComplaintResponse> {
    return this.http.get<ComplaintResponse>(`${this.apiUrl}/complaint/get/${id}`);
  }

  updateComplaint(complaint: ComplaintDto): Observable<ComplaintResponse> {
    return this.http.put<ComplaintResponse>(`${this.apiUrl}/complaint/update`, complaint)
  }

  deleteComplaint(id: number): Observable<Complaint> {
    return this.http.delete<Complaint>(`${this.apiUrl}/complaint/delete/${id}`)
  }

  printLabel(complaint: ComplaintDto): Observable<Complaint> {
    return this.http.post<Complaint>(`${this.apiUrl}/complaint/print-label`, complaint)
  }
*/


}
