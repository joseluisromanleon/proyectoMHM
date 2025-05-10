import {inject, Injectable} from "@angular/core";
import {MatDialog} from "@angular/material/dialog";
import { ComponentType} from "@angular/cdk/portal";
import {Mensaje} from "@features/mensaje";


// mejor poner aqui los metodos del modal  abrir y cerrar  e injectar esta clase en los lugares que se usaran

@Injectable({providedIn: "root"})
export class ModalService {

  private readonly _dialog = inject(MatDialog);

  constructor() {}

  /*Tipo de componente genérico Contacto, producto, proveedor etc.
   data T genérico  (no sabemos cuál será la data  genérico tb) */

  openModal<CT, T>(componentRef: ComponentType<CT>, data?: T, isEditing = false) {

    const config = {data, isEditing}; //reunimos los dos valores en la variable

    this._dialog.open(componentRef, {
      data: config,
      width: '600px'
    });
  }

  closeModal(): void{
  this._dialog.closeAll();
  }


}
