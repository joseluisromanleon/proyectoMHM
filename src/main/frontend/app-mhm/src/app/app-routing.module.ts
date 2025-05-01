import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
//import { ClienteIndexComponent } from './cliente/cliente-index.component';
//import { ProductoIndexComponent } from './producto/producto-index.component';
import { CategoriaIndexComponent} from "./categoria/index/categoria-index.component";
import {MensajeIndexComponent} from "./mensaje/index/mensaje-index.component";

const routes: Routes = [
  //{ path: '', component: HomeComponent },
  //{ path: 'cliente/index', component: ClienteIndexComponent },
  //{ path: 'producto/index', component: ProductoIndexComponent },
  { path: 'categoria/index', component: CategoriaIndexComponent },
  { path: 'mensaje/index', component: MensajeIndexComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]

})
export class AppRoutingModule { }
