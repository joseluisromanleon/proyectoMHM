import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { AbstractControl, FormGroup, ReactiveFormsModule, Validators, FormBuilder } from '@angular/forms';
import { NgClass, NgIf } from '@angular/common';
import {NgbActiveModal, NgbModalModule} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-login-modal',
  standalone: true,
  imports: [NgbModalModule ,ReactiveFormsModule, NgClass, NgIf],
  templateUrl: './modal-login.component.html',
  styleUrl: './modal-login.component.css'
})
export class ModalLoginComponent implements OnInit {

  loginForm!: FormGroup;
  submitted = false;
  error: string | null = null;

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    public activeModal: NgbActiveModal // <-- Inyecta el servicio del modal activo
  ) {}

  ngOnInit() {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.maxLength(100)]],
      password: ['', [Validators.required, Validators.maxLength(255)]],
    });
  }

  get f(): { [key: string]: AbstractControl } {
    return this.loginForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    const credentials = this.loginForm.value;
    this.authService.login(credentials).subscribe({
      next: (res: any) => {
        // Guarda todos los datos necesarios
        localStorage.setItem('token', res.token);
        localStorage.setItem('roles', JSON.stringify(res.roles));
        localStorage.setItem('estado', res.estado);
        localStorage.setItem('username', credentials.username);

        // Notifica al AuthService
        this.authService.setLoggedIn(true);
        this.error = null;
        this.activeModal.close('login-success'); // <-- Cierra el modal
      },
      error: (err) => {
        this.error = 'Usuario o contraseña incorrectos';
      }
    });
  }

  onCancel(): void {
    this.activeModal.dismiss('login-cancel');
  }
}
