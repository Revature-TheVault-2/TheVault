import { Component, Input, OnInit } from '@angular/core';
import { Account } from 'src/app/models/account/account.model';
import { Transaction } from 'src/app/models/transaction/transaction.model';
import { GlobalStorageService } from 'src/app/_services/global-storage.service';
<<<<<<< HEAD
import { TransactionHandlerService } from 'src/app/_services/transactions/transaction-handler.service';
import { GetTransaction } from 'src/app/models/transaction/responses/get-transaction';
=======
>>>>>>> 64f8b9ae0f6865205fdcbd0304034fcf6c382dd2

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
  constructor(
    private handler: TransactionHandlerService,
    private globalStorage: GlobalStorageService
  ) { }
=======
  constructor(private globalStorage: GlobalStorageService) { }
>>>>>>> 64f8b9ae0f6865205fdcbd0304034fcf6c382dd2

  ngOnInit(): void {
<<<<<<< HEAD
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
    if(selectedYear && selectedMonth) {
      this.handler.getTransactionHistoryByMonth(this.globalStorage.getActiveAccount().accountId, selectedYear, selectedMonth, this.globalStorage.getProfile().profileId).subscribe(this.myObserver);
    } else {
      console.error("ERROR: Please enter a valid date.");
    }
=======
    this.globalStorage.transFail = false;
    this.globalStorage.transSuccess = false;
>>>>>>> 64f8b9ae0f6865205fdcbd0304034fcf6c382dd2
  }
}
