import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({providedIn: 'root'})
export class AuthService {
  private apiUrl = 'http://localhost:8080/v1/api/auth/login';
  private isLoggedInSubject = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this.isLoggedInSubject.asObservable();

  constructor(private http: HttpClient) {
    // Al iniciar, revisa si hay token en localStorage
    const token = localStorage.getItem('token');
    this.isLoggedInSubject.next(!!token);
  }

  // auth.service.ts
  login(credentials: { username: string, password: string }) {
    return this.http.post(this.apiUrl, credentials);
  }

  logout() {
    localStorage.clear();
    localStorage.removeItem('token');
    this.isLoggedInSubject.next(false);
  }

  // auth.service.ts
  getRoles(): string[] {
    return JSON.parse(localStorage.getItem('roles') || '[]');
  }

  getEstado(): string {
    return localStorage.getItem('estado') || '';
  }

  getName(): string {
    return localStorage.getItem('username') || '';
  }

  setLoggedIn(value: boolean) {
    this.isLoggedInSubject.next(value);
  }


  isAdmin(): boolean {
    return this.getRoles().includes('ROLE_ADMIN');
  }

  isCliente(): boolean {
    return this.getRoles().includes('ROLE_CLIENTE');
  }

  isEmpleado(): boolean {
    return this.getRoles().includes('ROLE_EMPLEADO');
  }

  isSinRoles(): boolean {
    return this.getRoles().length === 0;
  }

  isActivo(): boolean {
    return this.getEstado() === 'ACEPTADO';
  }
}
