import {Component, inject, OnInit} from '@angular/core'
import { MatDialog } from '@angular/material/dialog';
import { MensajeFormModalComponent } from './components/mensaje/modal/mensaje-form-modal.component';
import {ModalService} from "./components/modal/modal.service";
import {MatCardModule} from "@angular/material/card";
import {RouterOutlet} from "@angular/router";
import {CommonModule, NgIf} from "@angular/common";
import {AppModule} from "./app.module";

//const MATERIAL_MODULES = [MatCardModule, MatDialog]

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [RouterOutlet, NgIf, AppModule, CommonModule, MatCardModule]// AppModule]
})
export class AppComponent implements OnInit{
  title = 'app-mhm';
  // Variables para controlar el estado del usuario
  isLoggedIn: boolean = false; // Indica si el usuario está logueado
  userRole: string = '';       // Rol del usuario (admin, cliente, proveedor, etc.)
  username: string = '';       // Nombre del usuario

  private readonly _modalSvc = inject(ModalService)

  //constructor(public _dialog:MatDialog) {}

  ngOnInit(): void {
    // Verificar el estado de autenticación al cargar el componente
    this.isLoggedIn = this.checkLoginStatus();
    if (this.isLoggedIn) {
      this.userRole = this.getUserRole();
      this.username = this.getUserName();
    }

}

  onClickNewContact(): void {
    const dialogRef = this._modalSvc.openModal<MensajeFormModalComponent>(MensajeFormModalComponent, {
      width: '600px', // Ajusta el tamaño
      data: {mensaje:'Hola Mundo'} // Puedes pasar datos iniciales si es necesario o eliminarlos
    });
    // dialogRef.afterClosed().subscribe(result => {
    //   console.log('Modal cerrado', result);
    //   // Aquí puedes recargar datos o mostrar mensajes
    // });
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
