import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { BehaviorSubject, catchError, map, Observable, of, startWith } from 'rxjs';
import { AppState } from '../app-state';
import { ComplaintDto } from '../complaint/complaintDto';
import { DataState } from '../enum/data-state.enum';
import { ComplaintResponse } from '../response/complaint-response';
import { ComplaintService } from '../service/complaint.service';

@Component({
  selector: 'app-complaint-list',
  templateUrl: './complaint-list.component.html',
  styleUrls: ['./complaint-list.component.css']
})
export class ComplaintListComponent implements OnInit {
  

  appState$: Observable<AppState<ComplaintResponse>>;
  readonly DataState = DataState;
  private dataSubject = new BehaviorSubject<ComplaintResponse>(null);
  private isLoading = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoading.asObservable();


  constructor(private complaintService: ComplaintService){}

  ngOnInit(): void {
    this.getClaims();
  }

  public getClaims(): void{
    this.appState$ = this.complaintService.complaints$
    .pipe(
      map(response => {
        return { dataState: DataState.LOADED_STATE, appData: response }
      }),
      startWith({ dataState: DataState.LOADING_STATE }),
      catchError((error: string) => {
        return of({ dataState: DataState.ERROR_STATE })
      } )
    );
  }

  public saveComplaint(complaintForm: NgForm): void {
    this.isLoading.next(true);
    this.appState$ = this.complaintService.save$(complaintForm.value as ComplaintDto )  //as ComplaintDto
      .pipe(
        map( response => {
          this.dataSubject.next(
            {...response, data: { complaints: [response.data.complaint, ...this.dataSubject.value.data.complaints]}}
          );
          document.getElementById('closeModal').click();
          this.isLoading.next(false);
          complaintForm.resetForm();
          return { dataState: DataState.LOADED_STATE, appData: this.dataSubject.value}
        }),
        startWith({ dataState: DataState.LOADED_STATE }),
        catchError((error: string) => {
        this.isLoading.next(false);
        return of({ dataState: DataState.ERROR_STATE })
      } )
      );
  }

}
