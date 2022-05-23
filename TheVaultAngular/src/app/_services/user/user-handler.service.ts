import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PostLogin } from 'src/app/models/login/responses/post-login';
import { LoginUser } from 'src/app/models/users/login-user.model';
import { NewUser } from 'src/app/models/users/new-user.model';
import { Profile } from 'src/app/models/users/profile.model';
import { GetProfile } from 'src/app/models/users/responses/get-profile';
import { PostProfile } from 'src/app/models/users/responses/post-profile';
import { PutProfile } from 'src/app/models/users/responses/put-profile';

const AUTH_API = 'http://localhost:9000/';

const ENDPOINTS = {
  LOGIN: `${AUTH_API}login`,
  NEW_LOGIN: `${AUTH_API}login/create`,
  VALIDATE: `${AUTH_API}login/validate`,
  CREATE_PROFILE: `${AUTH_API}profile/create`,
  GET_PROFILE: `${AUTH_API}profile/get/`,
  UPDATE_PROFILE: `${AUTH_API}profile/update`
}

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserHandlerService {
  
  constructor(
    private http: HttpClient
  ) { }

  credentials: LoginUser = new LoginUser("", "");

  validateLogin(credentials: LoginUser) {
    this.credentials = credentials;
    httpOptions.headers = httpOptions.headers.append('authorization', 'Basic ' + btoa(credentials.username + ':' + credentials.password));
    return this.http.post<PostLogin>(
      `${ENDPOINTS.LOGIN}`,
      {
        username: credentials.username
      },
      httpOptions);
  }

  getUserProfile(userId: number) {
    // httpOptions.headers = httpOptions.headers.append('authorization', 'Basic ' + btoa(this.credentials.username + ':' + this.credentials.password));
    return this.http.get<GetProfile>(
    `${ENDPOINTS.GET_PROFILE + userId}`,
    httpOptions);
  }

  createNewLogin(username: string, password: string) {
    console.log(username);
    // httpOptions.headers = httpOptions.headers.append('authorization', 'Basic ' + btoa(this.credentials.username + ':' + this.credentials.password));
    return this.http.post<PostLogin>(
      ENDPOINTS.NEW_LOGIN,
      JSON.stringify(
        {
          username: username,
          password: password
        }
      ), httpOptions);
  }

  createProfile(userId: number, newUser: NewUser) {
    // httpOptions.headers = httpOptions.headers.append('authorization', 'Basic ' + btoa(this.credentials.username + ':' + this.credentials.password));
    return this.http.post<PostProfile>(
      ENDPOINTS.CREATE_PROFILE,
      JSON.stringify(
        {
          userId: userId,
          firstName: newUser.firstName,
          lastName: newUser.lastName,
          email: newUser.email,
          phoneNumber: newUser.phoneNumber,
          address: newUser.address
        }
      ),
      httpOptions);
  }

  updateProfile(profile: Profile, profileId: number, userId: number) {
    // httpOptions.headers = httpOptions.headers.append('authorization', 'Basic ' + btoa(this.credentials.username + ':' + this.credentials.password));
    return this.http.put<PutProfile>(
      ENDPOINTS.UPDATE_PROFILE,
      JSON.stringify(
        {
          profileId: profileId,
          userId: userId,
          firstName: profile.firstName,
          lastName: profile.lastName,
          email: profile.email,
          phoneNumber: profile.phoneNumber,
          address: profile.address
        }
      ),
      httpOptions);
  }
}
