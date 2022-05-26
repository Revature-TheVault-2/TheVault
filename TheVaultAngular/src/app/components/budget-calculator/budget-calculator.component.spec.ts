import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BudgetCalculatorComponent } from './budget-calculator.component';

describe('BudgetCalculatorComponent', () => {
  let component: BudgetCalculatorComponent;
  let fixture: ComponentFixture<BudgetCalculatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BudgetCalculatorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BudgetCalculatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
