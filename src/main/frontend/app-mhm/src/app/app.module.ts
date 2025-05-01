import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {CategoriaModule} from "./categoria/categoria.module";
import {CategoriaRoutingModule} from "./categoria/categoria-routing.module";
import {CategoriaModalComponent} from "./categoria/Detalle/categoria-modal.component";
import {CarruselComponent} from "./carrusel/carrusel.component";
import {MensajeDetalleModalComponent} from "./mensaje/Detalle/mensaje-detalle-modal.component";
import {MensajeModule} from "./mensaje/mensaje.module";
import {MensajeRoutingModule} from "./mensaje/mensaje-routing.module";

@NgModule({
  declarations: [
    AppComponent,
    CarruselComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    MensajeModule,
    MensajeRoutingModule,
    CategoriaModule,
    CategoriaRoutingModule,
    HttpClientModule,
    CategoriaModalComponent,
    MensajeDetalleModalComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
