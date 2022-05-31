import { TestBed } from '@angular/core/testing';

import { TransactionByMonthService } from './transaction-by-month.service';

describe('TransactionByMonthService', () => {
  let service: TransactionByMonthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransactionByMonthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
