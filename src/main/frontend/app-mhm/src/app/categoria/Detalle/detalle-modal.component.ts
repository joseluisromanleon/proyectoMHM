import { Component, Input } from '@angular/core';
import { Categoria } from '../categoria';
import {NgIf} from "@angular/common";  // Asegúrate de que la ruta sea correcta

@Component({
  selector: 'app-detalle-modal',
  templateUrl: './detalle-modal.component.html',
  standalone: true,
  imports: [
    NgIf
  ],
  styleUrls: ['./detalle-modal.component.css']
})
export class DetalleModalComponent {

  // El input para recibir la categoría seleccionada desde el componente padre (el index)
  @Input() categoria: Categoria | undefined;

  constructor() {}

  // No hace falta agregar más lógica, porque el modal solo muestra datos
  // Si necesitas otras funcionalidades, puedes agregarlas aquí.
}
