import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MensajeService } from "../mensaje.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Mensaje } from "../mensaje";

@Component({
  selector: 'app-edit-mensaje',
  templateUrl: './mensaje-edit.component.html',
  styleUrls: ['./mensaje-edit.component.css']
})
export class MensajeEditComponent implements OnInit {

  id: number = 0;
  mensaje: Mensaje = {} as Mensaje;

  // Formulario con un control de 'nombre'
  form: FormGroup = new FormGroup({
    nombre: new FormControl('', [ Validators.required, Validators.pattern('^[a-zA-ZÁáÀàÉéÈèÍíÌìÓóÒòÚúÙùÑñüÜ \-\']+') ])
  });


  constructor(
    public mensajeService: MensajeService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.mensajeService.findById(this.id).subscribe((mensaje) => {
      this.mensaje = mensaje;

      // Establecer el valor del campo de nombre en el formulario
      this.form.get('nombre')?.setValue(this.mensaje.nombre);
    });
  }

  get f() {
    return this.form.controls;
  }

  submit() {
    if (this.form.valid) {  // Validar que el formulario es válido
      this.mensaje.nombre = this.form.value.nombre;  // Asignar el valor del formulario al mensaje

      this.mensajeService.update(this.id, this.mensaje).subscribe(res => {
        console.log('Mensaje actualizado satisfactoriamente!', res);
        this.router.navigateByUrl('mensaje/index').then();
      }, error => {
        console.error('Error al actualizar el mensaje', error);
      });
    }
  }
}
