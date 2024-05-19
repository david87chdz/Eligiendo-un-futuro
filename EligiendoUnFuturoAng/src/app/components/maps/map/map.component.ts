import { Component, Input, OnChanges, SimpleChanges, OnInit } from '@angular/core';
import * as L from 'leaflet';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnChanges, OnInit {
  @Input() latitud: number | undefined;
  @Input() longitud: number | undefined;
  @Input() schoolLatitud: number | undefined;
  @Input() schoolLongitud: number | undefined;

  private map: L.Map | undefined;

  constructor() { }

  ngOnInit() {
    this.initMap();
  }


  ngOnChanges(changes: SimpleChanges): void {
    if (changes['latitud'] || changes['longitud'] || changes['schoolLatitud'] || changes['schoolLongitud']) {
      this.initMap();
    }
  }

  /**
   * Initializes the map component.
   * Sets up the map view, adds tile layer, and places markers.
   * If the cordenates are not provided, it will use default values
   * for the latitude and longitude of Palencia.
   */
  private initMap(): void {
    let defaultLatitud = 42.00955;
    let defaultLongitud = -4.52406;

    if (this.map) {
      this.map.remove();
    }

    this.map = L.map('map').setView([this.latitud || defaultLatitud, this.longitud || defaultLongitud], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(this.map);

    L.marker([this.latitud || defaultLatitud, this.longitud || defaultLongitud]).addTo(this.map)
      .bindPopup('¡Aquí estás!')
      .openPopup();

    if (this.schoolLatitud && this.schoolLongitud) {
      L.marker([this.schoolLatitud, this.schoolLongitud]).addTo(this.map)
        .bindPopup('¡Aquí está la escuela!')
        .openPopup();
    }
  }
}
