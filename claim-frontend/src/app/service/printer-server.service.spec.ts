import { TestBed } from '@angular/core/testing';

import { PrinterServerService } from './printer-server.service';

describe('PrinterServerService', () => {
  let service: PrinterServerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrinterServerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
