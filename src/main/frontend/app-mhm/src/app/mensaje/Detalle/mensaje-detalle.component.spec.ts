import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MensajeDetalleComponent } from './mensaje-detalle.component';

describe('CreateComponent', () => {
  let component: MensajeDetalleComponent;
  let fixture: ComponentFixture<MensajeDetalleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MensajeDetalleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MensajeDetalleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
