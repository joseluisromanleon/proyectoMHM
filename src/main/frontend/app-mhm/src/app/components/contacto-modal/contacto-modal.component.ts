import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, AbstractControl } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-contacto-modal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './contacto-modal.component.html',
  styleUrls: ['./contacto-modal.component.css'],
})
export class ContactoModalComponent implements OnInit, AfterViewInit {
  contactForm!: FormGroup;
  submitted = false;

  private apiUrl = 'http://localhost:8080/v1/api/mensajes';

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
      aceptaCondiciones: [false, Validators.requiredTrue]
    });
  }

  ngAfterViewInit() {
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


  enviarMensaje() {
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
