import { HttpClient, HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ComplaintListComponent } from './complaint-list/complaint-list.component';
import { ComplaintService } from './service/complaint.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [
    AppComponent,
    ComplaintListComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,          // needed to add because the two way data binding -> wthout it in the input the course.component html and ts didnt work
    ReactiveFormsModule, 
    NgbModule
  ],
  providers: [
    ComplaintService,
    HttpClient
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
