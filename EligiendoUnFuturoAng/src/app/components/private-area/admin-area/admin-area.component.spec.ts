import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAreaComponent } from './admin-area.component';

describe('AdminAreaComponent', () => {
  let component: AdminAreaComponent;
  let fixture: ComponentFixture<AdminAreaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminAreaComponent]
    });
    fixture = TestBed.createComponent(AdminAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
