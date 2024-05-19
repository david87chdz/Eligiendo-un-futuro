import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { schoolGuard } from './school.guard';

describe('schoolGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => schoolGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
