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

  constructor() { }

  ngOnInit(): void {
  }

}
