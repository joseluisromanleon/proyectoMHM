import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { CategoriaIndexComponent } from './index/categoria-index.component';
import { CategoriaCreateComponent } from './create/categoria-create.component';
import { CategoriaEditComponent } from './edit/categoria-edit.component';
import { CategoriaDetalleComponent } from "./Detalle/categoria-detalle.component";

const routes: Routes = [
  { path: 'categoria', redirectTo: 'categoria/index', pathMatch: 'full'},
  { path: 'categoria/index', component: CategoriaIndexComponent },
  { path: 'categoria/create', component: CategoriaCreateComponent },
  { path: 'categoria/edit/:id', component: CategoriaEditComponent },
  { path: 'categoria/detalle/:id', component: CategoriaDetalleComponent}

];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CategoriaRoutingModule { }
