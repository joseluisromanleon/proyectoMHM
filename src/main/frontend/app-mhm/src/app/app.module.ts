import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './nav_bar/app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {CategoriaModule} from "./categoria/categoria.module";
import {CategoriaRoutingModule} from "./categoria/categoria-routing.module";
import {DetalleModalComponent} from "./categoria/Detalle/detalle-modal.component";

@NgModule({
  declarations: [
    AppComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    CategoriaModule,
    CategoriaRoutingModule,
    HttpClientModule,
    DetalleModalComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
