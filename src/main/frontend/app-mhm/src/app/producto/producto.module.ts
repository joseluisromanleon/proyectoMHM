import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { IndexComponent } from './index/index.component';
import { CreateComponent } from './create/create.component';
import { EditComponent } from './edit/edit.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {ProductoRoutingModule} from "./producto-routing.module";


@NgModule({
  declarations: [
    IndexComponent,
    CreateComponent,
    EditComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    ProductoRoutingModule
  ]
})
export class ProductoModule { }
