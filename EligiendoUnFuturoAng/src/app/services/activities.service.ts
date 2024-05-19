import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Activities } from '../interfaces/Activities';

@Injectable({
  providedIn: 'root'
})
export class ActivitiesService {

  constructor(private http: HttpClient) { }

  /**
   * Retrieves activities by school ID.
   * @param {number} id - The ID of the school.
   * @returns An Observable of Activities array.
   */
  getActivitiesBySchoolId(id: number): Observable<Activities[]> {
    return this.http.get<Activities[]>(`http://localhost:8083/api/colegios/actividades/${id}`);
  }

  /**
   * Retrieves all activities from the server.
   * @returns An Observable that emits an array of Activities.
   */
  getAllActivities(): Observable<Activities[]> {
    return this.http.get<Activities[]>(`http://localhost:8083/api/actividades`);
  }

  /**
   * Deletes an activity from a school.
   * @param {number} schoolId - The ID of the school.
   * @param {number} activityId - The ID of the activity.
   * @returns {any} - The result of the delete operation.
   */
  deleteActivityFromSchool(schoolId: number, activityId: number): any {
    return this.http.delete(`http://localhost:8083/api/ofertas/${schoolId}/${activityId}/delete`);
  }


  /**
   * Retrieves the list of offers from the server with activities and schools.
   * @returns An Observable that emits the response from the server.
   */
  getOferts(): Observable<any> {
    return this.http.get(`http://localhost:8083/api/ofertas`);
  }
}
