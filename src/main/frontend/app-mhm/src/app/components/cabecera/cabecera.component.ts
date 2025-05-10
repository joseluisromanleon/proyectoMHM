import {Component, HostListener, OnInit} from '@angular/core';
import {NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-cabecera',
  standalone: true,
    imports: [
        NgIf,
        RouterLink
    ],
  templateUrl: './cabecera.component.html',
  styleUrl: './cabecera.component.css'
})
export class CabeceraComponent implements OnInit{
  //variable para controlar el tamano de la pantalla
  isMobile = window.innerWidth < 765;

// Variables para controlar el estado del usuario
  isLoggedIn: boolean = false; // Indica si el usuario está logueado
  userRole: string = '';       // Rol del usuario (admin, cliente, proveedor, etc.)
  username: string = '';       // Nombre del usuario

  // private readonly _modalSvc = inject(ModalService)


  ngOnInit(): void {
    // Verificar el estado de autenticación al cargar el componente
    this.isLoggedIn = this.checkLoginStatus();
    if (this.isLoggedIn) {
      this.userRole = this.getUserRole();
      this.username = this.getUserName();
    }
  }

  onClickNewContact(): void {
    // const dialogRef = this._modalSvc.openModal<MensajeFormModalComponent>(MensajeFormModalComponent, {
    //   width: '600px', // Ajusta el tamaño
    //   data: {mensaje:'Hola Mundo'} // Puedes pasar datos iniciales si es necesario o eliminarlos
    // });
    // dialogRef.afterClosed().subscribe(result => {
    //   console.log('Modal cerrado', result);
    //   // Aquí puedes recargar datos o mostrar mensajes
    // });
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
