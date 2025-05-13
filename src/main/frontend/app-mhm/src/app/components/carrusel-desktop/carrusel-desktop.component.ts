import {AfterViewInit, Component, OnInit} from '@angular/core';
import { CommonModule} from '@angular/common';

declare var bootstrap: any;

@Component({
  selector: 'app-carrusel-desktop',
  standalone: true,
  templateUrl: './carrusel-desktop.component.html',
  styleUrls: ['./carrusel-desktop.component.css'],
  imports: [CommonModule],
})
export class CarruselDesktopComponent implements AfterViewInit, OnInit {
  isMobile = false;

  ngOnInit() {
    this.checkScreen();
    window.addEventListener('resize', () => this.checkScreen());
  }

  ngAfterViewInit(): void {
    const superior = document.getElementById('carruselSuperior');
    const inferior = document.getElementById('carruselInferior');
    const mobile = document.getElementById('carruselMovil')

    if (superior && inferior ) {
      const bsSuperior = bootstrap.Carousel.getOrCreateInstance(superior, { interval: false });
      const bsInferior = bootstrap.Carousel.getOrCreateInstance(inferior, { interval: false });

      setInterval(() => {
        bsSuperior.next(); // norma
        bsInferior.prev(); // inverso
      }, 5000);
    }else if (mobile){
      const bsMobile = bootstrap.Carousel.getOrCreateInstance(mobile, { interval: false });

      setInterval(() => {
        bsMobile.next(); // norma
      }, 5000);
    }
  }

  checkScreen() {
    this.isMobile = window.innerWidth < 768;
  }

}
// Nota: Forzamos el intervalo a false para evitar que Bootstrap lo gestione automáticamente.
