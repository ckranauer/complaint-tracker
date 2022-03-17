import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { throwError, Observable } from 'rxjs';
import { tap,catchError  } from 'rxjs/operators';
import { ComplaintResponse } from '../response/complaint-response';
import { Complaint } from '../complaint/complaint';
import { ComplaintDto } from '../complaint/complaintDto';
import { PaginationObj } from '../interface/pagination-obj';
import { ComplaintCreateDto } from '../complaint/complaintCreateDto';
import { ComplaintUpdateDto } from '../complaint/complaintUpdateDto';

@Injectable({
  providedIn: 'root'
})
export class ComplaintService {
  // Complainttracker-env.eba-zqnmujjv.us-east-1.elasticbeanstalk.com
  private readonly apiUrl = 'http://complainttracker-env.eba-zqnmujjv.us-east-1.elasticbeanstalk.com';//'http://localhost:8080';

  constructor(private http: HttpClient) { }


  createComplaint(complaint: ComplaintCreateDto): Observable<ComplaintResponse> {
    return this.http.post<ComplaintResponse>(`${this.apiUrl}/complaint/save`, complaint)
  }

  getComplaints(paginationObj: PaginationObj): Observable<ComplaintResponse> {
      return this.http.post<ComplaintResponse>(`${this.apiUrl}/complaint/list`, paginationObj);
  }

  getComplaint(id :number): Observable<ComplaintResponse> {
    return this.http.get<ComplaintResponse>(`${this.apiUrl}/complaint/get/${id}`);
  }

  searchComplaint(complaint: ComplaintDto): Observable<ComplaintResponse> {
    return this.http.get<ComplaintResponse>(`${this.apiUrl}/complaint/search/${complaint.serialNumber}`)
  }

  updateComplaint(complaint: ComplaintUpdateDto): Observable<ComplaintResponse> {
    return this.http.put<ComplaintResponse>(`${this.apiUrl}/complaint/update`, complaint)
  }
/*
  updateComplaint(complaint: ComplaintDto): Observable<Complaint> {
    return this.http.put<Complaint>(`${this.apiUrl}/complaint/update/${complaint.id}`, complaint)
  }*/

  deleteComplaint(id: number): Observable<Complaint> {
    return this.http.delete<Complaint>(`${this.apiUrl}/complaint/delete/${id}`)
  }

  printLabel(complaint: ComplaintDto): Observable<Complaint> {
    return this.http.post<Complaint>(`${this.apiUrl}/complaint/print-label`, complaint)
  }

  printLabelFromDB(id: number): Observable<ComplaintResponse> {
    return this.http.get<ComplaintResponse>(`${this.apiUrl}/complaint/print-label-from-saved-complaint/${id}`);
  }

  // "/create-report/{id}"
  // this.complaintService.createReport(id).subscribe();

  createReport(id: number): Observable<ComplaintResponse> {
    return this.http.get<ComplaintResponse>(`${this.apiUrl}/complaint/create-report/${id}`);
  }


/*
  complaints$ = <Observable<ComplaintResponse>>
  this.http.get<ComplaintResponse>(`${this.apiUrl}/complaint/list`)
  .pipe(
    tap(console.log),
    catchError(this.handleError)
  );

  save$ = (complaint: ComplaintDto) => <Observable<ComplaintResponse>>
  this.http.post<ComplaintResponse>(`${this.apiUrl}/complaint/save`, complaint)
  .pipe(
    tap(console.log),
    catchError(this.handleError)
  );

  handleError(handleError: any): Observable<never>{
    return throwError ('method not implemented.');
  }
  */
}
