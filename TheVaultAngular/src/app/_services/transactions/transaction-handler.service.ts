import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PutAccount } from 'src/app/models/account/responses/put-account';
import { DepositRequest } from 'src/app/models/transaction/request/deposit-request.model';
import { TransferRequest } from 'src/app/models/transaction/request/transfer-request.model';
import { WithdrawRequest } from 'src/app/models/transaction/request/withdraw-request.model';
import { DeleteDeposit } from 'src/app/models/transaction/responses/delete-deposit';
import { DeleteWithdraw } from 'src/app/models/transaction/responses/delete-withdraw';
import { GetTransaction } from 'src/app/models/transaction/responses/get-transaction';
import { PostDeposit } from 'src/app/models/transaction/responses/post-deposit';
import { PostWithdraw } from 'src/app/models/transaction/responses/post-withdraw';
import { Withdraw } from 'src/app/models/transaction/withdraw.model';
import { GlobalStorageService } from '../global-storage.service';


const AUTH_API = 'http://ec2-44-201-212-50.compute-1.amazonaws.com:9000/';

const ENDPOINTS = {
  CREATE_DEPOSIT: `${AUTH_API}deposit/create`,
  CREATE_WITHDRAW: `${AUTH_API}withdraw/generate`,
  DELETE_ALL_DEPOSIT: `${AUTH_API}deposit/clear/`,
  DELETE_ALL_WITHDRAW: `${AUTH_API}withdraw/clear/`,
  TRANSACTION_DETAILS: `${AUTH_API}withdraw/detail/`,
  TRANSACTION_HISTORY: `${AUTH_API}transaction/history/`
}

@Injectable({
  providedIn: 'root'
})
export class TransactionHandlerService {

  constructor(
    private http: HttpClient,
    private globalStorage: GlobalStorageService
  ) { }

  getTransactionDetails(withdrawId: number) {
    return this.http.get<Withdraw>(
      `${ENDPOINTS.TRANSACTION_DETAILS + withdrawId}`,
      this.globalStorage.getHttpOptions()
    )
  }

  createDeposit(deposit: DepositRequest) {
    return this.http.post<PostDeposit>(
      ENDPOINTS.CREATE_DEPOSIT,
      JSON.stringify(
        {
          depositType: deposit.depositType,
          accountId: deposit.accountId,
          reference: deposit.reference,
          amount: deposit.amount
        }
      ), this.globalStorage.getHttpOptions());
  }

  deleteAllDeposits(accountId: number) {
    return this.http.delete<DeleteDeposit>(
      `${ENDPOINTS.DELETE_ALL_DEPOSIT + accountId}`, this.globalStorage.getHttpOptions());
  }

  createWithdraw(withdraw: WithdrawRequest) {
    console.log(withdraw)
    return this.http.post<PostWithdraw>(
      ENDPOINTS.CREATE_WITHDRAW,
      JSON.stringify(
        {
          accountId: withdraw.accountId,
          requestType: withdraw.requestType,
          reference: withdraw.reference,
          amount: withdraw.amount
        }
      ), this.globalStorage.getHttpOptions());
  }

  deleteAllWithdraws(accountId: number) {
    return this.http.delete<DeleteWithdraw>(`${ENDPOINTS.DELETE_ALL_WITHDRAW + accountId}`, this.globalStorage.getHttpOptions())
  }

  getTransactionHistory(accountId: number) {
    return this.http.get<GetTransaction>(`${ENDPOINTS.TRANSACTION_HISTORY + accountId}`, this.globalStorage.getHttpOptions())
  }

  getTransactionHistoryByMonth(accountId:number,year:number,month:number,profileId:number){
    return this.http.get<GetTransaction>(`${ENDPOINTS.TRANSACTION_HISTORY + accountId + '/' + year + '/' + month + '/' + profileId}`, this.globalStorage.getHttpOptions())
  }

}
