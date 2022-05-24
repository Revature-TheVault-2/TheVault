import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { GetTransaction } from 'src/app/models/transaction/responses/get-transaction';
import { Observable, of } from 'rxjs';
import { Transaction } from 'src/app/models/transaction/transaction.model';
@Injectable({
  providedIn: 'root'
})
export class TransactionHistoryService {
<<<<<<< HEAD
    transactionUrl = `http://localhost:9000/transaction/history`;
    getUrl?: any
  httpOptions = {
    headers: new HttpHeaders ({'Content-Type': 'application/json'})
  }
=======
  transactionUrl = `http://localhost:9000/transaction/history`;
  getUrl?: any
>>>>>>> 384355c41b043888f4bc7d8b78858e89b0d359ce

  private transHistory = 'api/transactions'
  constructor(private http: HttpClient) { }
  getHistory(accountId: number) {
    this.getUrl = `${this.transactionUrl}/${accountId}`
    return this.http.get<GetTransaction>(this.getUrl)
  }

}
