import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {MensajeRoutingModule} from "./mensaje-routing.module";
import { MensajeIndexComponent } from './index/mensaje-index.component';
import { MensajeCreateComponent } from './create/mensaje-create.component';
import { MensajeEditComponent } from './edit/mensaje-edit.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MensajeDetalleModalComponent} from "./Detalle/mensaje-detalle-modal.component";


@NgModule({
  declarations: [
    MensajeIndexComponent,
    MensajeCreateComponent,
    MensajeEditComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    MensajeRoutingModule,
    MensajeDetalleModalComponent,
    MensajeDetalleModalComponent
  ]
})
export class MensajeModule { }
