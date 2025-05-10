import {AfterViewInit, Component} from '@angular/core';

declare var bootstrap: any;

@Component({
  selector: 'app-carrusel-desktop',
  standalone: true,
  templateUrl: './carrusel-desktop.component.html',
  styleUrls: ['./carrusel-desktop.component.css'],
})
export class CarruselDesktopComponent implements AfterViewInit {

  ngAfterViewInit(): void {
    const superior = document.getElementById('carruselSuperior');
    const inferior = document.getElementById('carruselInferior');

    if (superior && inferior) {
      const bsSuperior = bootstrap.Carousel.getOrCreateInstance(superior, { interval: false });
      const bsInferior = bootstrap.Carousel.getOrCreateInstance(inferior, { interval: false });

      setInterval(() => {
        bsSuperior.next(); // norma
        bsInferior.prev(); // inverso
      }, 5000);
    }
  }
}
// Nota: Forzamos el intervalo a false para evitar que Bootstrap lo gestione automáticamente.
