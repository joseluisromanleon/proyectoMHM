import {Component, HostListener, OnInit} from '@angular/core';
import {NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {NavComponent} from '../nav/nav.component';

@Component({
  selector: 'app-cabecera',
  standalone: true,
  imports: [
    NgIf,
    RouterLink,
    NavComponent,
  ],
  templateUrl: './cabecera.component.html',
  styleUrl: './cabecera.component.css'
})
export class CabeceraComponent{


}
