import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {CommonModule} from "@angular/common";
import {MatDialog} from "@angular/material/dialog";
import {MensajeService} from "../mensaje.service";
import {Mensaje} from "@features/mensaje";  // Asegúrate de que está la interfaz Mensaje

@Component({
  selector: 'app-detalle-mensaje',
  templateUrl: './mensaje-detalle.component.html',
  styleUrls: ['./mensaje-detalle.component.css'],
  standalone: true,
  imports: [CommonModule, RouterLink] // Importa los módulos necesarios directamente aquí
})
export class MensajeDetalleComponent implements OnInit {
  mensaje: Mensaje | undefined;  // Usamos Mensaje como tipo de datos

  constructor(
    private route: ActivatedRoute,
    private mensajeService: MensajeService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.mensajeService.findById(+id).subscribe({
        next: (data: Mensaje) => {
          this.mensaje = data;  // Asignamos los datos de la API al objeto mensaje
        },
        error: (error) => {
          console.error('Error al cargar el mensaje', error);
        }
      });

    }
  }
}
