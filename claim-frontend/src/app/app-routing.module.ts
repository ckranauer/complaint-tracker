import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ComplaintListComponent } from './complaint-list/complaint-list.component';
import { PrinterServerComponent } from './printer-server/printer-server.component';

const routes: Routes = [
 // { path: 'users', component: UserListComponent },
  { path: '', component: ComplaintListComponent },
  { path: 'complaints', component: ComplaintListComponent },
  { path: 'printer-servers', component: PrinterServerComponent }
 // { path: 'add-user', component: ProfileEditorComponent },
 // { path: 'add-complaint', component: ComplaintSaveComponent},

  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
