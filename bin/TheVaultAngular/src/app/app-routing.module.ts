import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppBootstrapComponent } from './app-bootstrap/app-bootstrap.component';
import { AccountProfileComponent } from './components/account-profile/account-profile.component';
import { AccountDetailComponent } from './components/account/account-detail/account-detail.component';
import { TransactionHistoryComponent } from './components/account/account-transactions/history/transaction-history/transaction-history.component';
import { AccountViewComponent } from './components/account/account-view/account-view.component';
import { BudgetCalculatorComponent } from './components/budget-calculator/budget-calculator.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { LoginComponent } from './components/login-register/login/login.component';
import { NewpasswordComponent } from './components/login-register/newpassword/newpassword.component';
import { RegisterComponent } from './components/login-register/register/register.component';
import { ResetpasswordComponent } from './components/login-register/resetpassword/resetpassword.component';
import { DepositGenerateComponent } from './components/transaction/deposit-generate/deposit-generate.component';
import { TransferGenerateComponent } from './components/transaction/transfer-generate/transfer-generate.component';
import { WithdrawGenerateComponent } from './components/transaction/withdraw-generate/withdraw-generate.component';

const routes: Routes = [
  {
    component: LoginComponent,
    path: ''
  },
  {
    component: RegisterComponent,
    path: 'register'
  },
  {
    component: AccountViewComponent,
    path: 'account-view'
  },
  {
    component: AccountDetailComponent,
    path: 'account-detail'
  },
  {
    component: AppBootstrapComponent,
    path: 'account-test'
  },
  {
    component: TransactionHistoryComponent,
    path: 'account-history'
  },
  {
    component: AccountProfileComponent,
    path: 'profile'
  },
  {
    component: DepositGenerateComponent,
    path: 'deposit'
  },
  {
    component: WithdrawGenerateComponent,
    path: 'withdraw-test'
  },
  {
    component: EditProfileComponent,
    path: 'edit-profile'
  },
  {
    component: TransactionHistoryComponent,
    path:'rickRoll'
  },
  {
    component: TransferGenerateComponent,
    path: 'account-transfer'
  },
  {
    component: ResetpasswordComponent,
    path: 'resetpassword'
  },
  {
    component: NewpasswordComponent,
    path: 'newpassword'
  },
  {
    component: BudgetCalculatorComponent,
    path: 'budget-calculator'

  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
