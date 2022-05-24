import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountTransactionByMonthComponent } from './account-transaction-by-month.component';

describe('AccountTransactionByMonthComponent', () => {
  let component: AccountTransactionByMonthComponent;
  let fixture: ComponentFixture<AccountTransactionByMonthComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountTransactionByMonthComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountTransactionByMonthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
