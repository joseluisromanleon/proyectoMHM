import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, AbstractControl } from '@angular/forms';
import { NgClass, NgIf } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { NgbActiveModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-contacto-modal',
  standalone: true,
  imports: [NgbModalModule, ReactiveFormsModule, NgIf, NgClass],
  templateUrl: './modal-contact.component.html',
  styleUrls: ['./modal-contact.component.css'],
})
export class ModalContactComponent implements OnInit {
  contactForm!: FormGroup;
  submitted = false;

  private apiUrl = 'http://localhost:8080/v1/api/mensajes';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    public activeModal: NgbActiveModal
  ) {}

  ngOnInit(): void {
    this.contactForm = this.fb.group({
      nombreEmpresa: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      direccionEmpresa: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      telEmpresa: ['', [Validators.required, Validators.pattern(/^\d{9}$/)]],
      emailEmpresa: ['', [Validators.required, Validators.email]],
      nombreContacto: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      telContacto: ['', [Validators.required, Validators.minLength(9), Validators.maxLength(12)]],
      emailContacto: ['', [Validators.required, Validators.email]],
      observaciones: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(256)]],
      aceptaCondiciones: [false, Validators.requiredTrue],
      enabled: [true]
    });
  }

  get f(): { [key: string]: AbstractControl } {
    return this.contactForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    this.contactForm.markAllAsTouched();

    if (this.contactForm.invalid) {
      return;
    }

    this.http.post(this.apiUrl, this.contactForm.value)
      .subscribe({
        next: () => {
          alert('Mensaje enviado correctamente');
          this.activeModal.close('contact-success');
        },
        error: () => alert('Error al enviar mensaje'),
      });
  }

  onCancel() {
    this.activeModal.dismiss('contact-cancel');
  }
}
