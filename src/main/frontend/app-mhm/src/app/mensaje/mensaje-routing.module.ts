import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MensajeIndexComponent } from './index/mensaje-index.component';
import { MensajeCreateComponent } from './create/mensaje-create.component';
import { MensajeEditComponent } from './edit/mensaje-edit.component';
import { MensajeDetalleComponent } from "./Detalle/mensaje-detalle.component";

const routes: Routes = [
  { path: 'mensaje', redirectTo: 'mensaje/index', pathMatch: 'full'},
  { path: 'mensaje/index', component: MensajeIndexComponent },
  { path: 'mensaje/create', component: MensajeCreateComponent },
  { path: 'mensaje/edit/:id', component: MensajeEditComponent },
  { path: 'mensaje/detalle/:id', component: MensajeDetalleComponent}

];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MensajeRoutingModule { }
