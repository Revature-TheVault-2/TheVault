import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GetTransaction } from 'src/app/models/transaction/responses/get-transaction';

@Injectable({
  providedIn: 'root'
})
export class TransactionByMonthService {
  transactionUrl = `http://ec2-35-174-165-54.compute-1.amazonaws.com:9000/transaction/history`;
  getUrl?: any
  httpOptions = {
  headers: new HttpHeaders ({'Content-Type': 'application/json'})
}

private transHistory = 'api/transactions'
constructor(private http: HttpClient) { }
getHistory(accountId:number,year:number,month:number) {
  this.getUrl = `${this.transactionUrl}/${accountId}/${year}/${month}`
  return this.http.get<GetTransaction>(this.getUrl)
}
}
