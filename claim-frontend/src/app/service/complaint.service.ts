import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { throwError, Observable } from 'rxjs';
import { tap,catchError  } from 'rxjs/operators';
import { ComplaintResponse } from '../response/complaint-response';
import { Complaint } from '../complaint/complaint';
import { ComplaintDto } from '../complaint/complaintDto';
import { PaginationObj } from '../interface/pagination-obj';
import { ComplaintCreateDto } from '../complaint/complaintCreateDto';

@Injectable({
  providedIn: 'root'
})
export class ComplaintService {

  private readonly apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }


  createComplaint(complaint: ComplaintCreateDto): Observable<Complaint> {
    return this.http.post<Complaint>(`${this.apiUrl}/complaint/save`, complaint)
  }

  getComplaints(paginationObj: PaginationObj): Observable<ComplaintResponse> {
      return this.http.post<ComplaintResponse>(`${this.apiUrl}/complaint/list`, paginationObj);
  }

  getComplaint(): Observable<Complaint> {
    return this.http.get<Complaint>(`${this.apiUrl}/complaint/get/1`);
  }

  updateComplaint(complaint: ComplaintDto, id:number): Observable<Complaint> {
    return this.http.put<Complaint>(`${this.apiUrl}/complaint/update/${id}`, complaint)
  }

  deleteComplaint(id: number): Observable<Complaint> {
    return this.http.delete<Complaint>(`${this.apiUrl}/complaint/delete/${id}`)
  }

  printLabel(complaint: ComplaintDto): Observable<Complaint> {
    return this.http.post<Complaint>(`${this.apiUrl}/complaint/print-label`, complaint)
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
