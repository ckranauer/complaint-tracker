import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { throwError, Observable } from 'rxjs';
import { tap,catchError  } from 'rxjs/operators';
import { ComplaintResponse } from '../response/complaint-response';
import { Complaint } from '../complaint/complaint';
import { ComplaintDto } from '../complaint/complaintDto';

@Injectable({
  providedIn: 'root'
})
export class ComplaintService {

  private readonly apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }


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
}
