import {Component, OnInit, } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {NgIf, NgOptimizedImage} from '@angular/common';
import {CarruselDesktopComponent} from './components/carrusel-desktop/carrusel-desktop.component';
import {CarruselMobileComponent} from './components/carrusel-mobile/carrusel-mobile.component';
import {CabeceraComponent} from './components/cabecera/cabecera.component';
import {FooterComponent} from './components/footer/footer.component';
import {ModalContactComponent} from './components/modal-contact/modal-contact.component';
import {NavComponent} from './components/nav/nav.component';
import {ModalRegisterComponent} from './components/modal-register/modal-register.component';
import {ModalLoginComponent} from './components/modal-login/modal-login.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgIf, RouterLink, NgOptimizedImage,
    CarruselDesktopComponent, CarruselMobileComponent,
    CabeceraComponent, FooterComponent, ModalContactComponent,
    NavComponent,  ModalRegisterComponent, ModalLoginComponent],

  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent  {



}
