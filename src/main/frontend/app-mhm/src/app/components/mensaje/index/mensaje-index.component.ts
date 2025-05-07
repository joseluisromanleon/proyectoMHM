import {Component, Input, OnInit} from '@angular/core';
import { MensajeService } from "../mensaje.service";
import { MensajeModule } from "../mensaje.module";


import { MatDialog } from '@angular/material/dialog';
import { MensajeFormModalComponent } from '../modal/mensaje-form-modal.component';
import {Mensaje} from "@features/mensaje";

@Component({
  selector: 'mensaje-index',
  templateUrl: './mensaje-index.component.html',
  styleUrls: ['./mensaje-index.component.css']
})
export class MensajeIndexComponent implements OnInit {

  mensajes: Mensaje[] = [];  // Cambiado a un array
  mensaje: Mensaje | undefined;  // Para el detalle
  mensajeService: MensajeService;

  //constructor(public mensajeService: MensajeService) { }

  // En el constructor:
  constructor(private dialog: MatDialog) {}

  ngOnInit(): void {
    this.cargarMensajes();
  }

  cargarMensajes(){
    this.mensajeService.getAll().subscribe((data: Mensaje[]) => {
      this.mensajes = data;
      console.log(this.mensajes);
    }, error => {
      console.error('Error al cargar el mensaje', error);
    });
  }

  deleteMensaje(id: number) {
    console.log("Entró en el metodo de index.ts");
    this.mensajeService.delete(id).subscribe(res => {
      this.mensajes = this.mensajes.filter(cat => cat.id !== id);
      console.log('Mensaje id =' + id + ' eliminado satisfactoriamente!');
    }, error => {
      console.error('Error al eliminar el mensaje', error);
    });
  }


  openEditModal(mensaje: Mensaje): void {
    this.mensaje = mensaje;
    const modalElement = document.getElementById('editModal');
    if (modalElement) {
      // Verifica si bootstrap está cargado correctamente
      const modal = new (window as any).bootstrap.Modal(modalElement);
      modal.show();
    }
  }

  openDetalleModal(mensaje: Mensaje): void {
    this.mensaje = mensaje;
    const modalElement = document.getElementById('detalleModal');
    if (modalElement) {
      // Verifica si bootstrap está cargado correctamente
      const modal = new (window as any).bootstrap.Modal(modalElement);
      modal.show();
    }
  }

  abrirModal() {
    const dialogRef = this.dialog.open(MensajeFormModalComponent, {
      width: '400px',
      panelClass: 'modal-transparente'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Recarga la lista de mensajes si se creó uno nuevo
        this.cargarMensajes();
      }
    });
  }


}
