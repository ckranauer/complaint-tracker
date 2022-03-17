import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { PaginationObj } from '../interface/pagination-obj';
import { UserResponse } from '../response/user-response';
import { UserService } from '../service/user.service';
import { UserCreateDto } from '../user/userCreateDto';
import { UserDto } from '../user/userDto';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users?: UserResponse;
  user?: UserResponse;

  private pageObj: PaginationObj = {
    "page": 0,
    "limit": 10
    
  }

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.onGetUsers(this.pageObj);
  }

  onGetUsers(pageObj: PaginationObj): void {
    this.userService.getUsers(pageObj)
    .subscribe(
      (response) => this.users = response,
      
      (error: any) => console.log(error),
      () => console.log('Done getting users')
      );
  }

  onGetUser(user: UserDto): void {
    this.userService.getUser(user.id)
    .subscribe(
      (response) => this.user = response,
      
      (error: any) => console.log(error),
      () => console.log('Done getting users')
      );
  }


  onCreateUser(userCreateForm: NgForm): void{
    this.userService.createUser(userCreateForm.value as UserCreateDto).subscribe(
      (response) => this.users = response,
      (error: any) => console.log(error),
      () => console.log('Done getting users'),
      ), userCreateForm.resetForm();
  }


  onUpdateUser(userUpdateForm: NgForm): void{
    console.log(userUpdateForm.value)
    this.userService.updateUser(userUpdateForm.value as UserDto).subscribe(
      (response) => this.users = response,
      (error: any) => console.log(error),
      () => console.log('Done getting users'),
      )
  }


}
