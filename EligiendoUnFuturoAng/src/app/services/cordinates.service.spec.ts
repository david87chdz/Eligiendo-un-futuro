import { TestBed } from '@angular/core/testing';

import { CordinatesService } from './cordinates.service';

describe('CordinatesService', () => {
  let service: CordinatesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CordinatesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
