import {Component, Input, OnInit} from '@angular/core';
import { Mensaje } from "../mensaje";
import { MensajeService } from "../mensaje.service";

@Component({
  selector: 'mensaje-index',
  templateUrl: './mensaje-index.component.html',
  styleUrls: ['./mensaje-index.component.css']
})
export class MensajeIndexComponent implements OnInit {

  mensajes: Mensaje[] = [];  // Cambiado a un array
  mensaje: Mensaje | undefined;  // Para el detalle

  constructor(public mensajeService: MensajeService) { }


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

}
