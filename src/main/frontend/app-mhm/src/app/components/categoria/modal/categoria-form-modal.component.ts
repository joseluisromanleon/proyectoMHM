import {Component, Inject, Input, } from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA, MatDialogModule, MatDialogContent} from '@angular/material/dialog';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatInputModule} from "@angular/material/input";
import {ModalService} from "../../modal/modal.service";
import {Categoria} from "@features/categoria";
import {CategoriaService} from "../categoria.service";


@Component({
  selector: 'categoria-form-modal',
  templateUrl: './categoria-form-modal.component.html',
  styleUrls: ['./categoria-form-modal.component.css'],
  standalone: true,
  imports: [
    MatDialogModule,
    ReactiveFormsModule,
    MatInputModule,
  ]

})


export class CategoriaFormModalComponent {
  form: FormGroup;

@Input() categoria:Categoria;
  constructor(
    private _modalService: ModalService,
    private fb: FormBuilder,
    private _categoriaService: CategoriaService,
    protected dialogRef: MatDialogRef<CategoriaFormModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.form = this.fb.group({
      nombreCategoria: ['', Validators.required],
    });
  }

    closeModal() {
      this._modalService.closeModal();
    }

    onSubmit() {
      if (this.form.valid) {
        this._categoriaService.create(this.form.value).subscribe(() => {
          this.dialogRef.close(true);
        });
      }
    }
}
