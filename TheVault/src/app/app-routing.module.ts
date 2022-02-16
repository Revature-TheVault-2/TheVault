import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppBootstrapComponent } from './app-bootstrap/app-bootstrap.component';
import { AccountDetailComponent } from './components/account/account-detail/account-detail.component';
import { AccountTransactionsComponent } from './components/account/account-transactions/account-transactions.component';
import { TransactionHistoryComponent } from './components/account/account-transactions/history/transaction-history/transaction-history.component';
import { AccountViewComponent } from './components/account/account-view/account-view.component';
import { SelectComponent } from './components/account/select/select.component';
import { LoginComponent } from './components/login-register/login/login.component';
import { RegisterComponent } from './components/login-register/register/register.component';

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
  {component: AppBootstrapComponent,
    path: 'account-test'
  },
  {
    component: AccountTransactionsComponent,
    path: 'account-withdraw'
  },
  {
    component: TransactionHistoryComponent,
    path: 'account-history'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
