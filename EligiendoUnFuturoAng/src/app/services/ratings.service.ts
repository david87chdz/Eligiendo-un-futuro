import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Evaluations } from '../interfaces/Evaluations';

@Injectable({
  providedIn: 'root'
})
export class RatingsService {
  [x: string]: any;

  constructor(private http: HttpClient) { }

  /**
   * Retrieves evaluations for a specific school ID.
   * @param {number} id - The ID of the school.
   * @returns An Observable that emits an array of Evaluations.
   */
  getEvaluationsSchoolId(id: number): Observable<Evaluations[]> {
    return this.http.get<Evaluations[]>(`http://localhost:8083/api/valoraciones/colegio/${id}`);
  }

  /**
   * Adds a rating to the server.
   * @param rating The rating to be added.
   * @returns An Observable.
   */
  addRating(rating: any): Observable<Evaluations> {
    return this.http.post<Evaluations>(`http://localhost:8083/api/valoraciones`, rating);
  }

  /**
   * Retrieves the ratings for a specific user.
   *
   * @param id - The ID of the user.
   * @returns An Observable that emits an array of Evaluations.
   */
  getRatingUser(id: number): Observable<Evaluations[]> {
    return this.http.get<Evaluations[]>(`http://localhost:8083/api/valoraciones/usuario/${id}`);
  }

  /**
   * Deletes a rating by its ID.
   * @param id - The ID of the rating to delete.
   * @returns An Observable.
   */
  deleteRating(id: number): Observable<Evaluations> {
    return this.http.delete<Evaluations>(`http://localhost:8083/api/valoraciones/${id}`);
  }
}
