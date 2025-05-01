import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MensajeEditComponent } from './mensaje-edit.component';

describe('EditComponent', () => {
  let component: MensajeEditComponent;
  let fixture: ComponentFixture<MensajeEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MensajeEditComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MensajeEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
