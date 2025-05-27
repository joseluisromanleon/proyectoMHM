import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { AuthService } from '../../services/auth.service';
import {AbstractControl, FormGroup, ReactiveFormsModule, Validators, FormBuilder} from '@angular/forms';
import {NgClass, NgIf} from '@angular/common';

@Component({
  selector: 'app-login-modal',
  standalone: true,
  imports: [ReactiveFormsModule, NgClass, NgIf
  ],
  templateUrl: './modal-login.component.html',
  styleUrl: './modal-login.component.css'
})
export class ModalLoginComponent implements OnInit, AfterViewInit{

  loginForm!: FormGroup;
  submitted=false;
  error: string | null = null;

  private apiUrl = 'http://localhost:8080/v1/api/auth/login';
  @ViewChild('loginModal') modalRef!: ElementRef;

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
        this.loginForm.reset(); // Resetear el formulario al abrir el modal
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
    console.log('Datos enviados:', credentials); // 👈 Mira qué envías

    this.authService.login(credentials).subscribe({
      next: (res: any) => {
        console.log('Respuesta del servidor:', res); // 👈 Mira qué recibes
        localStorage.setItem('token', res.token);
        this.error = null;

        // Opcional: cerrar el modal programáticamente
        //const modal = bootstrap.Modal.getInstance(this.modalRef.nativeElement);
        //modal?.hide();
      },
      error: (err) => {
        console.error('Error en login:', err); // 👈 Mira qué error recibes
        this.error = 'Usuario o contraseña incorrectos';
      }
    });
  }
}
