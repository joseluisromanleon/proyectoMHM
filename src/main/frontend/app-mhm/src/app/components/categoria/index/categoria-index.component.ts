import { Component, OnInit } from '@angular/core';
import { Categoria } from "@features/categoria";
import { CategoriaService } from "../categoria.service";

@Component({
  selector: 'app-index',
  templateUrl: './categoria-index.component.html',
  styleUrls: ['./categoria-index.component.css']
})
export class CategoriaIndexComponent implements OnInit {

  categorias: Categoria[] = [];  // Cambiado a un array
  categoria: Categoria | undefined;  // Para el detalle

  constructor(public categoriaService: CategoriaService) { }


  ngOnInit(): void {
    this.cargarCategorias();
  }

  cargarCategorias(){
    this.categoriaService.getAll().subscribe((data: Categoria[]) => {
      this.categorias = data;
      console.log(this.categorias);
    }, error => {
      console.error('Error al cargar categorías', error);
    });
  }

  deleteCategoria(id: number) {
    console.log("Entró en el metodo de index.ts");
    this.categoriaService.delete(id).subscribe(res => {
      this.categorias = this.categorias.filter(cat => cat.id !== id);
      console.log('Mensaje id =' + id + ' eliminada satisfactoriamente!');
    }, error => {
      console.error('Error al eliminar la categoría', error);
    });
  }


  openEditModal(categoria: Categoria): void {
    this.categoria = categoria;
    const modalElement = document.getElementById('editModal');
    if (modalElement) {
      // Verifica si bootstrap está cargado correctamente
      const modal = new (window as any).bootstrap.Modal(modalElement);
      modal.show();
    }
  }

  openDetalleModal(categoria: Categoria): void {
    this.categoria = categoria;
    const modalElement = document.getElementById('detalleModal');
    if (modalElement) {
      // Verifica si bootstrap está cargado correctamente
      const modal = new (window as any).bootstrap.Modal(modalElement);
      modal.show();
    }
  }

}
