import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { PaginationObj } from '../interface/pagination-obj';
import { PrinterServerResponse } from '../response/printer-server-response';
import { PrinterServerService } from '../service/printer-server.service';
import { PrinterServerCreateDto } from './dtos/printerServerCreateDto';

@Component({
  selector: 'app-printer-server',
  templateUrl: './printer-server.component.html',
  styleUrls: ['./printer-server.component.css']
})
export class PrinterServerComponent implements OnInit {


  servers?: PrinterServerResponse;
  server?: PrinterServerResponse;
  //complaint?: ComplaintDto;

  private pageObj: PaginationObj = {
    "page": 0,
    "limit": 10
    
  }

  constructor(private printerServerService: PrinterServerService) { }

  ngOnInit(): void {
    this.onGetServers(this.pageObj);
  }

  onGetServers(pageObj: PaginationObj): void {
    this.printerServerService.getServers(pageObj)
    .subscribe(
      (response) => this.servers = response,
      
      (error: any) => console.log(error),
      () => console.log('Done getting servers')
      );
  }


  onCreateServer(serverCreateForm: NgForm): void{
    this.printerServerService.createServer(serverCreateForm.value as PrinterServerCreateDto).subscribe(
      (response) => this.servers = response,
      (error: any) => console.log(error),
      () => console.log('Done getting servers'),
      ), serverCreateForm.resetForm();
  }

}
