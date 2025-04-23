import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { IndexComponent } from './index/index.component';
import { CreateComponent } from './create/create.component';
import { EditComponent } from './edit/edit.component';

const routes: Routes = [
  { path: 'producto', redirectTo: 'producto/index', pathMatch: 'full'},
  { path: 'producto/index', component: IndexComponent },
  { path: 'producto/create', component: CreateComponent },
  { path: 'producto/edit/:idProducto', component: EditComponent }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductoRoutingModule { }
