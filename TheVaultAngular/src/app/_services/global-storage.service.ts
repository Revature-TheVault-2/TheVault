import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Account } from '../models/account/account.model';
import { GetAccount } from '../models/account/responses/get-account';
import { LoginUser } from '../models/users/login-user.model';
import { Profile } from '../models/users/profile.model';

@Injectable({
  providedIn: 'root'
})
export class GlobalStorageService {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  userId!: number;
  userProfile!: Profile;
  username!: string;

  accountTypes: string[] = ["Checking", "Savings"];
  activeAccount!: Account;
  getAccount!: GetAccount;
  accounts!: Account[];
  transSuccess!: boolean;
  transFail!: boolean;

  token!:string;
  transSuccess : boolean = false;
  transFail : boolean = false;
  constructor() { }

  public setHttpOptions(credentials: LoginUser): void {
    this.resetHttpOptions();
    this.httpOptions.headers = this.httpOptions.headers.append('authorization', 'Basic ' + btoa(credentials.username + ':' + credentials.password));
  }

  public resetHttpOptions(): void {
    this.httpOptions.headers = this.httpOptions.headers.delete('authorization');
  }

  public getHttpOptions() {return this.httpOptions}

  public setProfile(user: Profile): void {this.userProfile = user}

  public getProfile():Profile{return this.userProfile}

  public setUsername(username:string){this.username = username}

  public getUsername():string{return this.username}

  public setUserId(userId: number){this.userId = userId}

  public getUserId():number{return this.userId}

  public setAccounts(accounts: Account[]){this.accounts = accounts}

  public getAccounts(): Account[]{return this.accounts}
  
  public addAccount(account:Account){this.accounts.push(account)}

  public setActiveAccount(account:Account){this.activeAccount = account}

  public getActiveAccount():Account{return this.activeAccount}

}
