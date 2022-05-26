import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Account } from 'src/app/models/account/account.model';
import { DeleteAccount } from 'src/app/models/account/responses/delete-account';
import { GetAccount } from 'src/app/models/account/responses/get-account';
import { PostAccount } from 'src/app/models/account/responses/post-account';
import { PutAccount } from 'src/app/models/account/responses/put-account';
import { TransferRequest } from 'src/app/models/transaction/request/transfer-request.model';
import { LoginUser } from 'src/app/models/users/login-user.model';
import { GlobalStorageService } from '../global-storage.service';

const AUTH_API = 'http://ec2-54-196-171-190.compute-1.amazonaws.com:9000/';


const ENDPOINTS = {
  CREATE_ACCOUNT: `${AUTH_API}account/create`,
  UPDATE_ACCOUNT: `${AUTH_API}account/update`,
  GET_ACCOUNT: `${AUTH_API}account/users-accounts?userId=`,
  DELETE_ACCOUNT: `${AUTH_API}account/delete?accountId=`,
  TRANSFER: `${AUTH_API}account/transfer`
}

@Injectable({
  providedIn: 'root'
})
export class AccountHandlerService {

  constructor(
    private http: HttpClient,
    private globalStorage: GlobalStorageService
  ) { }

  createAccount(userId: number, accountType: string) {
    return this.http.post<PostAccount>(
      ENDPOINTS.CREATE_ACCOUNT,
      JSON.stringify(
        {
          userId: userId,
          accountType: accountType
        }
      ), this.globalStorage.getHttpOptions());
  }

  updateAccount(updateAccount: Account) {
    return this.http.put<PutAccount>(
      ENDPOINTS.UPDATE_ACCOUNT,
      JSON.stringify(
        {
          accountId: updateAccount.accountId,
          accountType: updateAccount.accountType,
          availableBalance: updateAccount.availableBalance,
          pendingBalance: updateAccount.pendingBalance
        }
      ), this.globalStorage.getHttpOptions());
  }

  getAccounts(userId: number) {
    return this.http.get<GetAccount>(
      `${ENDPOINTS.GET_ACCOUNT + userId}`,
      this.globalStorage.getHttpOptions()
    );
  }

  deleteAccount(account: Account) {
    return this.http.delete<DeleteAccount>(
      `${ENDPOINTS.DELETE_ACCOUNT + account.accountId}`, this.globalStorage.getHttpOptions());
  }

  createTransfer(transfer: TransferRequest) {
    return this.http.put<PutAccount>(ENDPOINTS.TRANSFER, JSON.stringify({
      ownerAccountId: transfer.ownerAccountId,
      receiverAccountId: transfer.receiverAccountId,
      amount: transfer.amount
    }), this.globalStorage.getHttpOptions());
  }

}
