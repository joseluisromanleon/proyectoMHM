import { AuthService} from '../../services/auth.service';
import {Component, HostListener, OnInit} from '@angular/core';
import {NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [
    NgIf,
    RouterLink,
  ],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent implements OnInit{
  //variable para controlar el tamano de la pantalla
  isMobile = window.innerWidth < 765;
// Variables para controlar el estado del usuario
  isLoggedIn: boolean = false;  // Indica si el usuario está logueado
  userRole: string = '';        // Rol del usuario (admin, cliente, proveedor, etc.)
  username: string = '';        // Nombre del usuario
  roles: string[] = [];         // array de roles
  estado: string = '';          // estado del usuario

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // Suscríbete al observable para reaccionar a cambios de login/logout

    this.authService.isLoggedIn$.subscribe(isLogged => {
      this.isLoggedIn = isLogged;
      if (isLogged) {
        this.roles = this.authService.getRoles();
        this.username = this.authService.getName();
        this.estado = this.authService.getEstado();
      } else {
        this.roles = [];
        this.estado = '';
        this.username = '';
      }
    });
  }

  // Función para obtener el rol del usuario
  getUserRole(): string {
    // Simula la obtención del rol del usuario (por ejemplo, desde localStorage)
    return localStorage.getItem('userRole') || '';
  }

  // Función para obtener el nombre del usuario
  getUserName(): string {
    // Simula la obtención del nombre del usuario (por ejemplo, desde localStorage)
    return localStorage.getItem('username') || 'Usuario';
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.isMobile = window.innerWidth < 765;
  }
}
