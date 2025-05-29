import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { NgIf, NgClass } from '@angular/common';
import { NgbActiveModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-register-modal',
  standalone: true,
  imports: [NgbModalModule, ReactiveFormsModule, NgIf, NgClass], // <-- Añade NgbModalModule
  templateUrl: './modal-register.component.html',
  styleUrls: ['./modal-register.component.css']
})
export class ModalRegisterComponent implements OnInit {
  registerForm!: FormGroup;
  submitted = false;
  private apiUrl = 'http://localhost:8080/api/usuarios';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    public activeModal: NgbActiveModal // <-- Inyecta NgbActiveModal
  ) {}

  ngOnInit() {
    this.registerForm = this.fb.group({
      userName: ['', [Validators.required, Validators.maxLength(100)]],
      userEmail: ['', [Validators.email, Validators.maxLength(100)]],
      userPassword: ['', Validators.required],
      userAceptaCondiciones: [false, Validators.requiredTrue],
      enabled: [true]
    });
  }

  get f(): { [key: string]: AbstractControl } {
    return this.registerForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    this.registerForm.markAllAsTouched();

    if (this.registerForm.invalid) {
      return;
    }

    this.http.post(this.apiUrl, this.registerForm.value)
      .subscribe({
        next: () => {
          alert('Registro enviado correctamente');
          this.activeModal.close('register-success'); // <-- Cierra el modal al terminar
        },
        error: () => alert('Error al registrar usuario'),
      });
  }

  onCancel() {
    this.activeModal.dismiss('register-cancel');
  }
}
