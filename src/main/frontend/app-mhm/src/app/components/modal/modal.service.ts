import {inject, Injectable} from "@angular/core";
import {MatDialog} from "@angular/material/dialog";
import { ComponentType} from "@angular/cdk/portal";
import {Mensaje} from "@features/mensaje";


// mejor poner aqui los metodos del modal  abrir y cerrar  e injectar esta clase en los lugares que se usaran

@Injectable({providedIn: "root"})
export class ModalService {

  private readonly _dialog = inject(MatDialog);

  constructor() {}

  openModal<CT, T = Mensaje>(componentRef: ComponentType<CT>, data?: {
    data: { mensaje: string };
    width: string
  }, isEditing = false): void{

    const config = {data, isEditing};

    this._dialog.open(componentRef, {
      data: config,
      width: '600px'
    });
  }

  closeModal(): void{
  this._dialog.closeAll();
  }


}
