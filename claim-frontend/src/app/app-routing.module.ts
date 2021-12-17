import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ComplaintListComponent } from './complaint-list/complaint-list.component';

const routes: Routes = [
 // { path: 'users', component: UserListComponent },
  { path: '', component: ComplaintListComponent },
  { path: 'complaints', component: ComplaintListComponent },
 // { path: 'add-user', component: ProfileEditorComponent },
 // { path: 'add-complaint', component: ComplaintSaveComponent},

  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
