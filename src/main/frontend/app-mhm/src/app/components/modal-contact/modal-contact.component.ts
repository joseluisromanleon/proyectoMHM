import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, AbstractControl } from '@angular/forms';
import {NgClass, NgIf} from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-contacto-modal',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, NgClass],
  templateUrl: './modal-contact.component.html',
  styleUrls: ['./modal-contact.component.css'],
})
export class ModalContactComponent implements OnInit, AfterViewInit {
  contactForm!: FormGroup;
  submitted = false;

  private apiUrl = 'http://localhost:8080/v1/api/mensajes';

  // Resetea los datos cada vez que se abre el modal
  @ViewChild('contactModal') modalRef!: ElementRef;

  constructor(private fb: FormBuilder, private http: HttpClient) {}

  ngOnInit(): void {
    this.contactForm = this.fb.group({
      nombreEmpresa: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      direccionEmpresa: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      telEmpresa: ['', [Validators.required, Validators.pattern(/^\d{9}$/)]],
      emailEmpresa: ['', [Validators.required, Validators.email]],
      nombreContacto: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      telContacto: ['', [Validators.required, Validators.minLength(9), Validators.maxLength(12)]],
      emailContacto: ['', [Validators.required, Validators.email]],
      observaciones: ['',[Validators.required, Validators.minLength(3), Validators.maxLength(256)]],
      aceptaCondiciones: [false, Validators.requiredTrue],
      enabled: [true]
    });
  }

  ngAfterViewInit() {
    // Resetear el formulario cada vez que se abre el modal
    const modalEl = this.modalRef.nativeElement;
    modalEl.addEventListener('show.bs.modal', () => {
      this.contactForm.reset({
        aceptaCondiciones: false // El checkbox se desmarca por defecto
      });
      this.submitted = false;
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
        next: () => alert('Mensaje enviado correctamente'),
        error: () => alert('Error al enviar mensaje'),
      });
  }
}
