import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResetpasswordModalComponent } from './resetpassword-modal.component';

describe('ResetpasswordModalComponent', () => {
  let component: ResetpasswordModalComponent;
  let fixture: ComponentFixture<ResetpasswordModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResetpasswordModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetpasswordModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
