import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GlobalStorageService } from '../global-storage.service';
import { TransferRequest } from './../../models/transaction/request/transfer-request.model';

@Injectable({
  providedIn: 'root'
})
export class TransferGenerateService {

  constructor(
    private http: HttpClient,
    private globalStorage: GlobalStorageService
  ) { }

  createTransfer(transfer: TransferRequest) {
    let putTransferUrl = 'http://ec2-50-17-120-160.compute-1.amazonaws.com:9000/';
    console.log(transfer);
    return this.http.put<TransferRequest>(putTransferUrl, JSON.stringify({
      ownerAccountId: transfer.ownerAccountId,
      receiverAccountId: transfer.receiverAccountId,
      amount: transfer.amount
    }), this.globalStorage.getHttpOptions());
  }
}
