import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { CategoriaService } from "../categoria.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Categoria } from "@features/categoria";

@Component({
  selector: 'app-edit-categoria',
  templateUrl: './categoria-edit.component.html',
  styleUrls: ['./categoria-edit.component.css']
})
export class CategoriaEditComponent implements OnInit {

  id: number = 0;
  categoria: Categoria = {} as Categoria;

  // Formulario con un control de 'nombre'
  form: FormGroup = new FormGroup({
    nombre: new FormControl('', [ Validators.required, Validators.pattern('^[a-zA-ZÁáÀàÉéÈèÍíÌìÓóÒòÚúÙùÑñüÜ \-\']+') ])
  });


  constructor(
    public categoriaService: CategoriaService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.categoriaService.findById(this.id).subscribe((categoria) => {
      this.categoria = categoria;

      // Establecer el valor del campo de nombre en el formulario
      this.form.get('nombre')?.setValue(this.categoria.nombreCategoria);
    });
  }

  get f() {
    return this.form.controls;
  }

  submit() {
    if (this.form.valid) {  // Validar que el formulario es válido
      this.categoria.nombreCategoria = this.form.value.nombre;  // Asignar el valor del formulario a la categoría

      this.categoriaService.update(this.id, this.categoria).subscribe(res => {
        console.log('Categoría actualizada satisfactoriamente!', res);
        this.router.navigateByUrl('categoria/index').then();
      }, error => {
        console.error('Error al actualizar la categoría', error);
      });
    }
  }
}
