import {Component, OnInit} from '@angular/core';
import { Producto} from "../producto";
import {ProductoService} from "../producto.service";

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  productos: Producto[] = [];

  constructor(public productoService:ProductoService) {}

  ngOnInit(): void {
    this.productoService.getAll().subscribe((data: Producto[])=>{
      this.productos= data;
      console.log(this.productos);

    })
  }



  deleteProducto(id: number){
    console.log("Entró en el metodo de index.ts");
    this.productoService.delete(id).subscribe(res => {
      this.productos = this.productos.filter(cat => cat.id !== id);
      console.log('Producto id =' + id + ' eliminada satisfactoriamente!');
    })
  }


}

