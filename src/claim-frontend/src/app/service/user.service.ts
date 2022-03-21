import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PaginationObj } from '../interface/pagination-obj';
import { UserResponse } from '../response/user-response';
import { UserCreateDto } from '../user/userCreateDto';
import { UserDto } from '../user/userDto';

@Injectable({
  providedIn: 'root'
})
export class UserService {


  private readonly apiUrl = 'http://complainttracker-env.eba-zqnmujjv.us-east-1.elasticbeanstalk.com';// 'http://localhost:8080';
  //private readonly apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  createUser(user: UserCreateDto): Observable<UserResponse> {
    return this.http.post<UserResponse>(`${this.apiUrl}/user/save`, user)
  }

  getUsers(paginationObj: PaginationObj): Observable<UserResponse> {
    return this.http.post<UserResponse>(`${this.apiUrl}/user/list`, paginationObj);
  }

  getUser(id :number): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${this.apiUrl}/user/get/${id}`);
  }


  updateUser(user: UserDto): Observable<UserResponse> {
    return this.http.put<UserResponse>(`${this.apiUrl}/user/update`, user)
  }
}
