import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollegeAreaComponent } from './college-area.component';

describe('CollegeAreaComponent', () => {
  let component: CollegeAreaComponent;
  let fixture: ComponentFixture<CollegeAreaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CollegeAreaComponent]
    });
    fixture = TestBed.createComponent(CollegeAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
