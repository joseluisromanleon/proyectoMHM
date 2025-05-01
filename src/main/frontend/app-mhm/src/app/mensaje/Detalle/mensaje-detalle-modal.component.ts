import { Component, Input } from '@angular/core';
import { Mensaje } from '../mensaje';
import {NgIf} from "@angular/common";  // Asegúrate de que la ruta sea correcta

@Component({
  selector: 'mensaje-detalle-modal',
  templateUrl: './mensaje-detalle-modal.component.html',
  standalone: true,
  imports: [
    NgIf

  ],
  styleUrls: ['./mensaje-detalle-modal.component.css']
})
export class MensajeDetalleModalComponent {

  // El input para recibir la categoría seleccionada desde el componente padre (el index)
  @Input() mensaje: Mensaje | undefined;

  constructor() {}

  // No hace falta agregar más lógica, porque el modal solo muestra datos
  // Si necesitas otras funcionalidades, puedes agregarlas aquí.
}
