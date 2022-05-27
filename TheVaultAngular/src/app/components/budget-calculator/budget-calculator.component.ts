// import { Component, OnInit } from '@angular/core';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { RoutingAllocatorService } from 'src/app/_services/app_control/routing-allocator.service';
import { AccountHandlerService } from 'src/app/_services/account/account-handler.service';
import { Account } from 'src/app/models/account/account.model';
import { Expense } from 'src/app/models/budget-calculator-input/expenses.model';

@Component({
  selector: 'app-budget-calculator',
  templateUrl: './budget-calculator.component.html',
  styleUrls: ['./budget-calculator.component.css']
})
export class BudgetCalculatorComponent implements OnInit {
  
  incomeField: number = 0;
  // expensesField = "";
  expensesArray: Expense[] = [];
  budget: number = 0;
  
  constructor() { }

  clearTable(){
    this.clearExpenses();
    this.incomeField = 0;
    this.onClickSubmitIncome("0");
  }

  clearExpenses(){
    this.expensesArray = [];
    let strNum = JSON.stringify(this.incomeField);
    this.onClickSubmitIncome(strNum);
  }

  sumExpenses(){
    let sum = 0;
    for(let i=0; i < this.expensesArray.length; i++){
      sum += this.expensesArray[i].amount;
    }
    return sum;
  }

  onClickSubmitIncome(amount: string){
    this.incomeField = amount ? Number.parseFloat(amount) : this.incomeField;
    this.budget = this.incomeField - this.sumExpenses();
  }

  onClickSubmitExpenses(name: string, amount: string){
    if( !name || !amount ){
      return;
    }else{
      let expense = new Expense(name, Number.parseFloat(amount));
      this.expensesArray.push(expense);
      this.budget = this.incomeField - this.sumExpenses();
      console.log(this.expensesArray);
    }
  }

  ngOnInit(): void {
  }
}
