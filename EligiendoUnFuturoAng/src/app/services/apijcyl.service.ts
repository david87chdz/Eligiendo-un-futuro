import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApijcylService {

  constructor(private http: HttpClient) { }
  /* 
    Dont use because the api response with a CORS error 
    getAll(): any {
      const apiKey = 'dcerezo';
      const url = 'https://analisis.datosabiertos.jcyl.es/api/explore/v2.1/catalog/datasets/directorio-de-centros-docentes/records';
  
      return this.http.get('http://localhost:3000/api/directorio-de-centros-docentes');
  
    } */

  /**
   * Retrieves all the data from the JSON.
   * @returns {any} The results from the JSON.
   */
  getAll(): any {
    return this.http.get('../../assets/json/directorio-de-centros-docentes.json');
  }

}
