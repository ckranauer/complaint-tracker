import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrinterServerComponent } from './printer-server.component';

describe('PrinterServerComponent', () => {
  let component: PrinterServerComponent;
  let fixture: ComponentFixture<PrinterServerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrinterServerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PrinterServerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
