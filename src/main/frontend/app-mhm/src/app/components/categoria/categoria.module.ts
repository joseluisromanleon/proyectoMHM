import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {CategoriaRoutingModule} from "./categoria-routing.module";
import { CategoriaIndexComponent } from './index/categoria-index.component';
import { CategoriaCreateComponent } from './create/categoria-create.component';
import { CategoriaEditComponent } from './edit/categoria-edit.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {CategoriaFormModalComponent} from "./modal/categoria-form-modal.component";


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
        CategoriaFormModalComponent
    ]
})
export class CategoriaModule { }
