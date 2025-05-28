import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { AbstractControl, FormGroup, ReactiveFormsModule, Validators, FormBuilder } from '@angular/forms';
import { NgClass, NgIf } from '@angular/common';

@Component({
  selector: 'app-login-modal',
  standalone: true,
  imports: [ReactiveFormsModule, NgClass, NgIf],
  templateUrl: './modal-login.component.html',
  styleUrl: './modal-login.component.css'
})
export class ModalLoginComponent implements OnInit, AfterViewInit {

  loginForm!: FormGroup;
  submitted = false;
  error: string | null = null;

  @ViewChild('loginModal', { static: false }) modalRef!: ElementRef;

  constructor(private authService: AuthService, private fb: FormBuilder) {}

  ngOnInit() {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required, Validators.maxLength(100)]],
      password: ['', [Validators.required, Validators.maxLength(255)]],
    });
  }

  ngAfterViewInit() {
    // Resetear el formulario cada vez que se abre el modal
    if (this.modalRef && this.modalRef.nativeElement) {
      const modalEl = this.modalRef.nativeElement;
      modalEl.addEventListener('show.bs.modal', () => {
        this.loginForm.reset();
        this.submitted = false;
        this.error = null;
      });
    } else {
      console.warn('No se encontró el modal en el DOM');
    }
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
    console.log('Datos enviados:', credentials);

    this.authService.login(credentials).subscribe({
      next: (res: any) => {
        console.log('Respuesta del servidor:', res);

        // Guarda todos los datos necesarios
        localStorage.setItem('token', res.token);
        localStorage.setItem('roles', JSON.stringify(res.roles));
        localStorage.setItem('estado', res.estado);
        localStorage.setItem('username', credentials.username);

        // Notifica al AuthService
        this.authService.setLoggedIn(true);
        this.error = null;

       },
      error: (err) => {
        console.error('Error en login:', err);
        this.error = 'Usuario o contraseña incorrectos';
      }
    });
  }
}
