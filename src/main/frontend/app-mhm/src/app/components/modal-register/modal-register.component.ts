import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { NgIf, NgClass } from '@angular/common';

@Component({
  selector: 'app-register-modal',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, NgClass],
  templateUrl: './modal-register.component.html',
  styleUrls: ['./modal-register.component.css']
})
export class ModalRegisterComponent implements OnInit, AfterViewInit {
  registerForm!: FormGroup;
  submitted = false;

  private apiUrl = 'http://localhost:8080/v1/api/usuarios';

  @ViewChild('registerModal') modalRef!: ElementRef;

  constructor(private fb: FormBuilder, private http: HttpClient) {}

  ngOnInit() {
    this.registerForm = this.fb.group({
      userName: ['', [Validators.required, Validators.maxLength(100)]],
      userEmail: ['', [Validators.email, Validators.maxLength(100)]],
      userPassword: ['', Validators.required],
      userAceptaCondiciones: [false, Validators.requiredTrue],
      enabled: [true]
    });
  }

  ngAfterViewInit() {
    // Resetear el formulario cada vez que se abre el modal
    const modalEl = this.modalRef.nativeElement;
    modalEl.addEventListener('show.bs.modal', () => {
      this.registerForm.reset({
        enabled: true,
        aceptacondiciones: false  // El checkbox se desmarca por defecto
      });
      this.submitted = false;
    });
  }

  get f(): { [key: string]: AbstractControl } {
    return this.registerForm.controls;
  }

  onSubmit() {
    console.log(this.registerForm.value);
    this.submitted = true;
    this.registerForm.markAllAsTouched();

    if (this.registerForm.invalid) {
      return;
    }

    this.http.post(this.apiUrl, this.registerForm.value)
      .subscribe({
        next: () => alert('Registro enviado correctamente'),
        error: () => alert('Error al registrar usuario'),
      });
  }
}
