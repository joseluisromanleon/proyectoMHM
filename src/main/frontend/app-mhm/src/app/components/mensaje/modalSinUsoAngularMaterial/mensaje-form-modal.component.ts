import {Component, Inject, Input, } from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA, MatDialogModule, } from '@angular/material/dialog';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatFormField, MatInput,} from "@angular/material/input";
import {ModalService} from "../../modal/modal.service";
import {Mensaje} from "@features/mensaje";
import {MensajeService} from "../mensaje.service";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatButtonModule} from "@angular/material/button";


const MATERIAL_MODULES = [MatDialogModule, MatLabel, MatFormField, MatInput, ReactiveFormsModule]
@Component({
  selector: 'mensaje-form-modal',
  templateUrl: './mensaje-form-modal.component.html',
  styleUrls: ['./mensaje-form-modal.component.css'],
  standalone: true,
  imports: [
    MatDialogModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatFormField,
  ]

})


export class MensajeFormModalComponent {
  form: FormGroup;

@Input() mensaje:Mensaje;
  constructor(
    private _modalService: ModalService,
    private fb: FormBuilder,
    private _mensajeService: MensajeService,
    protected dialogRef: MatDialogRef<MensajeFormModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.form = this.fb.group({
      nombreEmpresa: ['', Validators.required],
      direccion: ['', Validators.required],
      cp: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(5)]],
      telefonoEmpresa: ['', [Validators.required, Validators.minLength(9), Validators.maxLength(9)]],
      emailEmpresa: ['', [Validators.required, Validators.email]],
      nombreContacto: ['', Validators.required],
      telefonoContacto: ['', [Validators.required, Validators.minLength(9), Validators.maxLength(9)]],
      emailContacto: ['', [Validators.required, Validators.email]],
      observaciones: ['']
    });
  }


    closeModal() {
      this._modalService.closeModal();
    }

    onSubmit() {
      if (this.form.valid) {
        this._mensajeService.create(this.form.value).subscribe(() => {
          this.dialogRef.close(true);
        });
      }
    }
  }

