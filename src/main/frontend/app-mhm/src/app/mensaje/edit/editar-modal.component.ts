import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, ReactiveFormsModule} from '@angular/forms';
import {MensajeService} from "../mensaje.service";


@Component({
  selector: 'app-editar-modal',
  templateUrl: './editar-modal.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  styleUrls: ['./editar-modal.component.css']
})
export class EditarModalComponent implements OnInit {
  mensaje: any;
  editarForm: FormGroup;

  constructor(private mensajeService: MensajeService) { }

  ngOnInit(): void {
    this.editarForm = new FormGroup({
      id: new FormControl(''),
      nombre: new FormControl('')
    });
  }

  editarMensaje(id, mensaje): void {
    // Llamar al servicio para editar el mensaje
    this.mensajeService.update(id,mensaje);
  }
}
