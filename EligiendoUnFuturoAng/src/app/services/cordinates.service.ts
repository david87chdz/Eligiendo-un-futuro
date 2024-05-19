import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CordinatesService {

  constructor(private http: HttpClient) { }

  /**
   * Retrieves the coordinates of a school based on its code.
   * @param code The code of the school.
   * @returns An Observable that emits the coordinates of the school if found, or null if not found.
   */
  getSchoolByCode(code: string) {
    return this.http.get('../../assets/json/directorio-de-centros-docentes.geojson').pipe(
      map((data: any) => {
        const foundElement = data.features.find((element: any) => element.properties.codigo === code);
        return foundElement ? foundElement.geometry.coordinates : null;
      })
    );
  }
}
