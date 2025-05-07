import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
//import { ClienteIndexComponent } from './cliente/cliente-index.component';
//import { ProductoIndexComponent } from './components/producto/index/producto-index.component';
import { CategoriaIndexComponent} from "./components/categoria/index/categoria-index.component";
import { MensajeIndexComponent} from "./components/mensaje/index/mensaje-index.component";
import {CommonModule} from "@angular/common";

const routes: Routes = [
  //{ path: '', component: HomeComponent },
  //{ path: 'cliente/index', component: ClienteIndexComponent },
  //{ path: 'producto/index', component: ProductoIndexComponent },
  //{ path: 'categoria', component: CategoriaIndexComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [],
  bootstrap: [],
})
export class AppRoutingModule { }
