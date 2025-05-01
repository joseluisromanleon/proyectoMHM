import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MensajeCreateComponent } from './mensaje-create.component';

describe('CreateComponent', () => {
  let component: MensajeCreateComponent;
  let fixture: ComponentFixture<MensajeCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MensajeCreateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MensajeCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
