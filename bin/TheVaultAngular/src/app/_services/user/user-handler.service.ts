import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Token } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { LoginCredential } from 'src/app/models/login/login-credential.model';
import { PostLogin } from 'src/app/models/login/responses/post-login';
// import { PostReset } from 'src/app/models/login/responses/post-reset';
import { LoginUser } from 'src/app/models/users/login-user.model';
import { NewUser } from 'src/app/models/users/new-user.model';
import { Profile } from 'src/app/models/users/profile.model';
import { GetProfile } from 'src/app/models/users/responses/get-profile';
import { PostProfile } from 'src/app/models/users/responses/post-profile';
import { PutProfile } from 'src/app/models/users/responses/put-profile';
import { GlobalStorageService } from '../global-storage.service';



const AUTH_API = 'http://ec2-35-174-165-54.compute-1.amazonaws.com:9000/';

const params = new HttpParams()
  .set('token','token');

const ENDPOINTS = {
  LOGIN: `${AUTH_API}login`,
  NEW_LOGIN: `${AUTH_API}create`,
  // VALIDATE: `${AUTH_API}login/validate`,
  CREATE_PROFILE: `${AUTH_API}profile/create`,
  GET_PROFILE: `${AUTH_API}profile/get/`,
  UPDATE_PROFILE: `${AUTH_API}profile/update`,
  RESET_PASSWORD: `${AUTH_API}resetpassword`,
  NEW_PASSWORD: `${AUTH_API}newpassword`,

}

@Injectable({
  providedIn: 'root'
})
export class UserHandlerService {
  
  constructor(
    private http: HttpClient,
    private globalStorage: GlobalStorageService
  ) { }

  validateLogin(credentials: LoginUser) {
    return this.http.post<PostLogin>(
      `${ENDPOINTS.LOGIN}`,
      {
        username: credentials.username,
        password: credentials.password
      },
      this.globalStorage.getHttpOptions());
  }

  getUserProfile(userId: number) {
    return this.http.get<GetProfile>(
    `${ENDPOINTS.GET_PROFILE + userId}`,
    this.globalStorage.getHttpOptions());
  }

  createNewLogin(username: string, password: string) {
    console.log(username);
    console.log(ENDPOINTS.NEW_LOGIN);
    return this.http.post<PostLogin>(
      ENDPOINTS.NEW_LOGIN,
      JSON.stringify(
        {
          username: username,
          password: password
        }
      ), this.globalStorage.getHttpOptions());
  }

  createProfile(userId: number, newUser: NewUser) {
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
      this.globalStorage.getHttpOptions());
  }

  updateProfile(profile: Profile, profileId: number, userId: number) {
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
          address: profile.address,
          notificationAmount: profile.notificationAmount
        }
      ),
      this.globalStorage.getHttpOptions());
  }

  resetPassword(username: string, password: string){
    
    console.log(username);
    console.log(ENDPOINTS.RESET_PASSWORD);
    return this.http.post<boolean>(
      ENDPOINTS.RESET_PASSWORD,
      JSON.stringify(
        {
          username:username,
          password:password
        }
      ),
      this.globalStorage.getHttpOptions());
      
  }

  newPassword(token: string, password: string){

   
    return this.http.post<boolean>(
      ENDPOINTS.NEW_PASSWORD,
      JSON.stringify(
        {
          token:token,
          password:password
        }
      ),
      this.globalStorage.getHttpOptions());
            
  
}
}
