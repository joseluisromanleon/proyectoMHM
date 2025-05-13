import {Component, HostListener, OnInit} from '@angular/core';
import {NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent implements OnInit{
  //variable para controlar el tamano de la pantalla
  isMobile = window.innerWidth < 765;
// Variables para controlar el estado del usuario
  isLoggedIn: boolean = false; // Indica si el usuario está logueado
  userRole: string = '';       // Rol del usuario (admin, cliente, proveedor, etc.)
  username: string = '';       // Nombre del usuario


  ngOnInit(): void {
    // Verificar el estado de autenticación al cargar el componente
    this.isLoggedIn = this.checkLoginStatus();
    if (this.isLoggedIn) {
      this.userRole = this.getUserRole();
      this.username = this.getUserName();
    }
  }


// Función para verificar si el usuario está logueado
  checkLoginStatus(): boolean {
    if (typeof window !== 'undefined' && window.localStorage) {
      return localStorage.getItem('isLoggedIn') === 'true';
    }
    return false;
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
