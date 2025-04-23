import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {CategoriaRoutingModule} from "./categoria-routing.module";
import { CategoriaIndexComponent } from './index/categoria-index.component';
import { CategoriaCreateComponent } from './create/categoria-create.component';
import { CategoriaEditComponent } from './edit/Categoria-edit.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {DetalleModalComponent} from "./Detalle/detalle-modal.component";


@NgModule({
  declarations: [
    CategoriaIndexComponent,
    CategoriaCreateComponent,
    CategoriaEditComponent
  ],
    imports: [
        FormsModule,
        ReactiveFormsModule,
        CommonModule,
        CategoriaRoutingModule,
        DetalleModalComponent
    ]
})
export class CategoriaModule { }
