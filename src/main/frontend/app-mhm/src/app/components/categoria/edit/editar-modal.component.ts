import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, ReactiveFormsModule} from '@angular/forms';
import {CategoriaService} from "../categoria.service";


@Component({
  selector: 'app-editar-modal',
  templateUrl: './editar-modal.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  styleUrls: ['./editar-categoria-form-modal.component.css']
})
export class EditarModalComponent implements OnInit {
  categoria: any;
  editarForm: FormGroup;

  constructor(private categoriaService: CategoriaService) { }

  ngOnInit(): void {
    this.editarForm = new FormGroup({
      id: new FormControl(''),
      nombre: new FormControl('')
    });
  }

  editarCategoria(id, categoria): void {
    // Llamar al servicio para editar la categoría
    this.categoriaService.update(id,categoria);
  }
}
