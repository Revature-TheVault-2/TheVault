import { Component, Input, OnInit } from '@angular/core';
import { Account } from 'src/app/models/account/account.model';
import { Transaction } from 'src/app/models/transaction/transaction.model';
<<<<<<< HEAD
import { GlobalStorageService } from 'src/app/_services/global-storage.service';
=======
import { TransactionByMonthService } from 'src/app/_services/transactions/transaction-by-month.service';
import { GlobalStorageService } from 'src/app/_services/global-storage.service';
import { TransactionHandlerService } from 'src/app/_services/transactions/transaction-handler.service';
import { GetTransaction } from 'src/app/models/transaction/responses/get-transaction';
>>>>>>> 38f11cde1cbe751113059a7ef0020f8835f59afb

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
  constructor(private globalStorage: GlobalStorageService) { }
=======
  constructor(
    private transactionByMonth: TransactionByMonthService,
    private handler: TransactionHandlerService,
    private globalStorage: GlobalStorageService
  ) { }
>>>>>>> 38f11cde1cbe751113059a7ef0020f8835f59afb

  ngOnInit(): void {
<<<<<<< HEAD
    this.globalStorage.transFail = false;
    this.globalStorage.transSuccess = false;
=======
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
    next: (x: GetTransaction) => this.transactions = x.gotObject,
    error: (err: Error) => console.error('ERROR: ' + err),
    complete: () => console.log('Transactions retrieved successfully!'),
  };

  // When we submit, retrieve the transaction history by month
  onSubmit(): void {
    console.log("Submit was hit! This is what the date value is set to: " + this.selected);
    let selectedDate = new Date(this.selected + '-01T00:00:00');
    console.log("And this is that date string as an object! " + selectedDate.getFullYear() + "-" + selectedDate.getMonth());
    let selectedYear = selectedDate.getFullYear();
    let selectedMonth = selectedDate.getMonth(); // Remember that this is zero based
    this.handler.getTransactionHistoryByMonth(this.globalStorage.userId, selectedYear, selectedMonth).subscribe(this.myObserver);
>>>>>>> 38f11cde1cbe751113059a7ef0020f8835f59afb
  }
}
