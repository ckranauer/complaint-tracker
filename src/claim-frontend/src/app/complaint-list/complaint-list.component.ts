import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { BehaviorSubject, catchError, map, Observable, of, startWith } from 'rxjs';
import { AppState } from '../app-state';
import { Complaint } from '../complaint/complaint';
import { ComplaintCreateDto } from '../complaint/complaintCreateDto';
import { ComplaintDto } from '../complaint/complaintDto';
import { DataState } from '../enum/data-state.enum';
import { PaginationObj } from '../interface/pagination-obj';
import { ComplaintResponse } from '../response/complaint-response';
import { ComplaintService } from '../service/complaint.service';
import { DatePipe } from '@angular/common';
import { ComplaintUpdateDto } from '../complaint/complaintUpdateDto';
import { UserService } from '../service/user.service';
import { UserResponse } from '../response/user-response';

@Component({
  selector: 'app-complaint-list',
  templateUrl: './complaint-list.component.html',
  styleUrls: ['./complaint-list.component.css']
})
export class ComplaintListComponent implements OnInit {
  
  @Output() pageChange: EventEmitter<number> = new EventEmitter();

  appState$: Observable<AppState<ComplaintResponse>>;
  readonly DataState = DataState;
  private dataSubject = new BehaviorSubject<ComplaintResponse>(null);
  private isLoading = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoading.asObservable();
  page = 0;
  limit = 10;
 

  claims?: ComplaintResponse;
  claim?: ComplaintResponse;
  complaint?: ComplaintDto;

  users?: UserResponse;
  user?: UserResponse;


  pageObj: PaginationObj = {
    "page": 1,
    "limit": 10
    
  }


  constructor(private complaintService: ComplaintService, private userService: UserService , private datePipe: DatePipe){}

  ngOnInit(): void {
    this.onGetComplaints();
    this.onGetUsers(this.pageObj);
    //this.onGetComplaints(this.page, this.limit);
    
  }
/*
  onChangePage(){
    this.onGetComplaints(this.pageObj);
  }
  */

 
/*
 onPageChange = (pageNumber) => {
  this.pageChange.emit(pageNumber)    
  this.pageObj.page = pageNumber;
  this.onGetComplaints();
  
   
}
*/
 



  onGetComplaint(complaint: ComplaintDto): void{
    this.complaintService.getComplaint(complaint.id).subscribe(
      (response) => this.claim = response, 
      (error: any) => console.log(error),
      () => console.log(this.claim)
      );
  }

  onSearchComplaint(complaintSearchForm: NgForm): void{
    this.complaintService.searchComplaint(complaintSearchForm.value as ComplaintDto).subscribe(
      (response) => this.claim = response, 
      (error: any) => console.log(error),
      () => console.log(this.claim)
      ),complaintSearchForm.resetForm();
  }

 

  onGetComplaints(): void {
    this.complaintService.getComplaints()
    .subscribe(
      (response) => this.claims = response,
      
      (error: any) => console.log(error),
      () => console.log('Done getting complaints')
      ), console.log(this.claims?.data?.complaints),
      console.log(this.claims);
  }

  onGetUsers(pageObj: PaginationObj): void {
    this.userService.getUsers(pageObj)
    .subscribe(
      (response) => this.users = response,
      
      (error: any) => console.log(error),
      () => console.log('Done getting users')
      );
  }

  onCreateComplaint(complaintCreateForm: NgForm): void{
    this.complaintService.createComplaint(complaintCreateForm.value as ComplaintCreateDto).subscribe(
      (response) => {
        //this.claims = response;
        console.log(response);
        this.onGetComplaints();
      },
      (error: any) => console.log(error),
      () => console.log('Done getting complaint'),
      ), complaintCreateForm.resetForm();
      
  }

  onUpdateComplaint(complaintUpdateForm: NgForm): void{
    console.log(complaintUpdateForm.value)
    this.complaintService.updateComplaint(complaintUpdateForm.value as ComplaintUpdateDto).subscribe(
      (response) => {
        this.claims = response;
        console.log(response);
      } ,
      (error: any) => console.log(error),
      () => console.log('Done getting complaint'),
      )
  }

  onDeleteComplaint(id: number): void{
    this.complaintService.deleteComplaint(id).subscribe(
      (response) => console.log(response), 
      (error: any) => console.log(error),
      () => console.log(this.claim)
      ),
      this.onGetComplaints();
  }

  onCreateAnalysisReport(id: number): void{
    console.log(id);
    this.complaintService.createReport(id).subscribe(
      (response) => console.log(response)
    );
  }

  onPrintLabelFromDB(id: number): void{
    console.log(id);
    this.complaintService.printLabelFromDB(id).subscribe(
      (response) => console.log(response)
    );
  }

  

 

  /*
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
  }*/

  /*
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
  }*/

}
