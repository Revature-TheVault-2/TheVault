import { Component, Input, OnInit } from '@angular/core';
import { Account } from 'src/app/models/account/account.model';
import { Transaction } from 'src/app/models/transaction/transaction.model';
<<<<<<< HEAD
<<<<<<< HEAD
import { GlobalStorageService } from 'src/app/_services/global-storage.service';
=======
import { TransactionByMonthService } from 'src/app/_services/transactions/transaction-by-month.service';
import { GlobalStorageService } from 'src/app/_services/global-storage.service';
import { TransactionHandlerService } from 'src/app/_services/transactions/transaction-handler.service';
import { GetTransaction } from 'src/app/models/transaction/responses/get-transaction';
>>>>>>> 38f11cde1cbe751113059a7ef0020f8835f59afb
=======
import { GlobalStorageService } from 'src/app/_services/global-storage.service';
import { TransactionHandlerService } from 'src/app/_services/transactions/transaction-handler.service';
import { GetTransaction } from 'src/app/models/transaction/responses/get-transaction';
>>>>>>> 8d557c4a04e08e1e12d1d3caa408235ff1484c96

@Component({
  selector: 'app-transaction-history',
  templateUrl: './transaction-history.component.html',
  styleUrls: ['./transaction-history.component.css']
})
export class TransactionHistoryComponent implements OnInit {
    /* istanbul ignore next */
  term!:string;
  selected!:string;
  /* istanbul ignore next */
  index?:number
  
  /* istanbul ignore next */
  @Input()
  account!: Account;
  todayString!: string;
  pastString!: string;
  
  /* istanbul ignore next */
  @Input()
  transactions: Transaction[] = [];

  /* istanbul ignore next */
<<<<<<< HEAD
<<<<<<< HEAD
  constructor(private globalStorage: GlobalStorageService) { }
=======
=======
>>>>>>> 8d557c4a04e08e1e12d1d3caa408235ff1484c96
  constructor(
    private handler: TransactionHandlerService,
    private globalStorage: GlobalStorageService
  ) { }
<<<<<<< HEAD
>>>>>>> 38f11cde1cbe751113059a7ef0020f8835f59afb

  ngOnInit(): void {
<<<<<<< HEAD
    this.globalStorage.transFail = false;
    this.globalStorage.transSuccess = false;
=======
=======

  ngOnInit(): void {
>>>>>>> 8d557c4a04e08e1e12d1d3caa408235ff1484c96
    let today = new Date();
    // let dd = String(today.getDate()).padStart(2, '0');
    let mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    let yyyy = today.getFullYear();
    let pastyyyy = today.getFullYear() - 100;
    this.todayString = yyyy + '-' + mm;
    this.selected = this.todayString;
    this.pastString = pastyyyy + '-' + mm;
  }

  // Create observer object
  myObserver = {
    next: (retrieved: GetTransaction) => this.onRetrievedTransactions(retrieved),
    error: (err: Error) => {
      this.globalStorage.transSuccess = false;
      this.globalStorage.transFail = true;
      console.error("No transactions found: " + err)
    }
  };

  onRetrievedTransactions(retrieved: GetTransaction) {
    this.transactions = retrieved.gotObject;
    if(this.transactions.length) {
      console.log("We found transactions for this month!");
    } else {
      console.log("Successfully executed, but no transactions found!");
    }
  }

  // When we submit, retrieve the transaction history by month
  onSubmit(): void {
    // console.log("Submit was hit! This is what the date value is set to: " + this.selected);
    let selectedDate = new Date(this.selected + '-01T00:00:00');
    // console.log("And this is that date string as an object! " + selectedDate.getFullYear() + "-" + selectedDate.getMonth());
    let selectedYear = selectedDate.getFullYear();
    let selectedMonth = selectedDate.getMonth(); // Remember that this is zero based
<<<<<<< HEAD
    this.handler.getTransactionHistoryByMonth(this.globalStorage.userId, selectedYear, selectedMonth).subscribe(this.myObserver);
>>>>>>> 38f11cde1cbe751113059a7ef0020f8835f59afb
=======
    if(selectedYear && selectedMonth) {
      this.handler.getTransactionHistoryByMonth(this.globalStorage.getActiveAccount().accountId, selectedYear, selectedMonth, this.globalStorage.getProfile().profileId).subscribe(this.myObserver);
    } else {
      console.error("ERROR: Please enter a valid date.");
    }
>>>>>>> 8d557c4a04e08e1e12d1d3caa408235ff1484c96
  }
}
