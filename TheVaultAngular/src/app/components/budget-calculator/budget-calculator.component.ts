// import { Component, OnInit } from '@angular/core';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { RoutingAllocatorService } from 'src/app/_services/app_control/routing-allocator.service';
import { AccountHandlerService } from 'src/app/_services/account/account-handler.service';
import { Account } from 'src/app/models/account/account.model';

@Component({
  selector: 'app-budget-calculator',
  templateUrl: './budget-calculator.component.html',
  styleUrls: ['./budget-calculator.component.css']
})
export class BudgetCalculatorComponent implements OnInit {

  incomeField = '';
  expensesField = "";
  expensesArray = [""];

  get income(){
    return this.incomeField;
  }
  set income(temp: string){
    this.incomeField = temp;
  }

  get expenses(){
    return this.expensesField;
  }
  set expenses(temp: string){
    this.expenses = temp;
  }

  onClickSubmitIncome(amount: string){
    this.incomeField = amount;
      console.log(amount);
  }

  onClickSubmitExpenses(amount: string){
    // this.expensesField = amount;
    this.expensesArray.push(amount);
      console.log(amount)
      console.log(this.expensesArray);
  }

  constructor() { }

  ngOnInit(): void {
  }

}
