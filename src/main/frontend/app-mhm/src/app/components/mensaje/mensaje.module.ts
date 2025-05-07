import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule} from "@angular/material/button";

import {MensajeRoutingModule} from "./mensaje-routing.module";
import { MensajeIndexComponent } from './index/mensaje-index.component';
import { MensajeFormModalComponent } from './modal/mensaje-form-modal.component';
import { MensajeEditComponent } from './edit/mensaje-edit.component';
import {MensajeCreateComponent} from "./create/mensaje-create.component";



@NgModule({
  declarations: [
    MensajeIndexComponent,
    MensajeEditComponent,
    MensajeCreateComponent,


  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    MensajeRoutingModule,
    MensajeFormModalComponent,
    MatButtonModule,
    MensajeFormModalComponent,
  ]
})
export class MensajeModule { }
