// Si usas app.routes.ts (standalone)
import { Routes } from '@angular/router';
import { MensajeDetalleComponent } from './components/mensaje/detalle/mensaje-detalle.component';
import {CarruselDesktopComponent} from './components/carrusel-desktop/carrusel-desktop.component';


export const routes: Routes = [
  // otras rutas...
  { path: './mensaje/detalle', component: MensajeDetalleComponent },
  { path: './carrusel', component: CarruselDesktopComponent},


  // ruta comodín para 404
  { path: '**', redirectTo: '', pathMatch: 'full' },
];
