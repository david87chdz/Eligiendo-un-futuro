import { TestBed } from '@angular/core/testing';

import { ApijcylService } from './apijcyl.service';

describe('ApijcylService', () => {
  let service: ApijcylService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApijcylService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
