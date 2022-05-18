import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { Account } from 'src/app/models/account/account.model';
import { DeleteAccount } from 'src/app/models/account/responses/delete-account';
import { GetAccount } from 'src/app/models/account/responses/get-account';
import { DeleteDeposit } from 'src/app/models/transaction/responses/delete-deposit';
import { DeleteWithdraw } from 'src/app/models/transaction/responses/delete-withdraw';
import { AccountHandlerService } from 'src/app/_services/account/account-handler.service';
import { GlobalStorageService } from 'src/app/_services/global-storage.service';
import { TransactionHandlerService } from 'src/app/_services/transactions/transaction-handler.service';

import { SelectComponent } from './select.component';

class MockGlobalStorage extends GlobalStorageService {
    public override getUserId(): number {
        return 1;
    }
}

class MockAccountHandler extends AccountHandlerService{
  public override getAccounts(userId: number): Observable<GetAccount> {
      let getAccounts: GetAccount = {
        success: true,
        gotObject: [
          new Account(1, userId, "Checking", 100, 100)
        ]
      }
      return of(getAccounts)
  }
}

class MockTransactionHandler extends TransactionHandlerService{
  public override deleteAllDeposits(accountId: number): Observable<DeleteDeposit> {
      return of();
  }

  public override deleteAllWithdraws(accountId: number): Observable<DeleteWithdraw> {
      return of();      
  }
}

describe('SelectComponent', () => {
  let component: SelectComponent;
  let fixture: ComponentFixture<SelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelectComponent ],
      imports: [HttpClientTestingModule],
      providers: [
        {
          providers: GlobalStorageService,
          useClass: MockGlobalStorage
        },
        {
          providers: AccountHandlerService,
          useClass: MockAccountHandler
        },
        {
          providers: TransactionHandlerService,
          useClass: MockTransactionHandler
        }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should on calling setupAccount, update the accounts', () => {
    const fixture = TestBed.createComponent(SelectComponent);
    const app = fixture.componentInstance;

    let getAccounts: GetAccount = {
      success: true,
      gotObject: [
        new Account(1, 1, "Checking", 100, 100)
      ]
    }

    let accountHandler = fixture.debugElement.injector.get(AccountHandlerService);
    let globalStorage = fixture.debugElement.injector.get(GlobalStorageService);

    let getAccountSpy = spyOn(accountHandler, "getAccounts").and.returnValue(of(getAccounts));
    let userIdSpy = spyOn(globalStorage, "getUserId").and.returnValue(1);

    app.setupAccounts();
    expect(getAccountSpy).toHaveBeenCalled();
    expect(userIdSpy).toHaveBeenCalled();
    expect(app.accounts[0]).toBe(getAccounts.gotObject[0]);
  });

  it('should delete the account and update the components current accounts', () => {
    const fixture = TestBed.createComponent(SelectComponent);
    const app = fixture.componentInstance;

    let account: Account = new Account(1, 1, "Checking", 100, 100);
    let index: number = 0;

    let deleteDeposits: DeleteDeposit = {
      success: true,
      deletedObject: []
    };

    let deleteWithdraws: DeleteWithdraw = {
      success: true,
      deletedObject: []
    };

    let deleteAccount: DeleteAccount = {
      success: true,
      deletedObject: [new Account(1, 1, "Checking", 100, 100)]
    };

    let getAccounts: GetAccount = {
      success: true,
      gotObject: []
    };

    let transactionHandler = fixture.debugElement.injector.get(TransactionHandlerService);
    let deleteAllDepositsSpy = spyOn(transactionHandler, "deleteAllDeposits").and.returnValue(of(deleteDeposits));
    let deleteAllWithdrawsSpy = spyOn(transactionHandler, "deleteAllWithdraws").and.returnValue(of(deleteWithdraws));

    let accountHandler = fixture.debugElement.injector.get(AccountHandlerService);
    let deleteAccountSpy = spyOn(accountHandler, "deleteAccount").and.returnValue(of(deleteAccount));
    let globalStorage = fixture.debugElement.injector.get(GlobalStorageService);

    let getAccountSpy = spyOn(accountHandler, "getAccounts").and.returnValue(of(getAccounts));
    let userIdSpy = spyOn(globalStorage, "getUserId").and.returnValue(1);

    app.accounts = [account];
    app.deleteAccount(account, index);
    expect(deleteAllDepositsSpy).toHaveBeenCalled();
    expect(deleteAllWithdrawsSpy).toHaveBeenCalled();
    expect(deleteAccountSpy).toHaveBeenCalled();
    expect(getAccountSpy).toHaveBeenCalled();
    expect(userIdSpy).toHaveBeenCalled();
    expect(app.accounts.length).toBe(0);
    
  });
  


});
