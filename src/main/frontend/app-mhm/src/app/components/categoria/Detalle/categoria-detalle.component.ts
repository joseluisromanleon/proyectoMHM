import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import { CategoriaService } from '../categoria.service';
import { Categoria } from '../../../features/categoria';
import {CommonModule} from "@angular/common";  // Asegúrate de que está la interfaz Mensaje

@Component({
  selector: 'categoria-detalle',
  templateUrl: './categoria-detalle.component.html',
  styleUrls: ['./categoria-detalle.component.css'],
  standalone: true,
  imports: [CommonModule, RouterLink] // Importa los módulos necesarios directamente aquí
})
export class CategoriaDetalleComponent implements OnInit {
  categoria: Categoria | undefined;  // Usamos Mensaje como tipo de datos

  constructor(
    private route: ActivatedRoute,
    private categoriaService: CategoriaService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.categoriaService.findById(+id).subscribe({
        next: (data: Categoria) => {
          this.categoria = data;  // Asignamos los datos de la API al objeto categoria
        },
        error: (error) => {
          console.error('Error al cargar la categoría', error);
        }
      });

    }
  }
}
