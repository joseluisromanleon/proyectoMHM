import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'app-mhm';
  // Variables para controlar el estado del usuario
  isLoggedIn: boolean = false; // Indica si el usuario está logueado
  userRole: string = '';       // Rol del usuario (admin, cliente, proveedor, etc.)
  username: string = '';       // Nombre del usuario

  constructor() {}

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
    // Simula la verificación del estado de autenticación (por ejemplo, desde localStorage)
    return localStorage.getItem('isLoggedIn') === 'true';
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
}
