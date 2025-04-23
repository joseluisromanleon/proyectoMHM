import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ProductoService} from "../producto.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Producto} from "../producto";
import {combineLatest, forkJoin} from "rxjs";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  id: number = 0;
  producto: Producto = {} as Producto;
  form: FormGroup =   new FormGroup({
    producto:  new FormControl('', [ Validators.required, Validators.pattern('^[a-zA-ZÁáÀàÉéÈèÍíÌìÓóÒòÚúÙùÑñüÜ \-\']+') ])
  });

  constructor(
    public productoService: ProductoService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['idProducto'];
      this.productoService.find(this.id).subscribe((producto) => {
      this.producto = producto;

      this.form.get('nombre')?.setValue(this.producto.nombre);
    });
  }

  get f(){
    return this.form.controls;
  }

  submit(){
    console.log(this.form.value);
    this.productoService.update(this.id, this.form.value).subscribe(res => {
      console.log('Categoría actualizada satisfactoriamente!');
      this.router.navigateByUrl('producto/index').then();
    })
  }

}
