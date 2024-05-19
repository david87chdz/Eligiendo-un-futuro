import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { School } from '../interfaces/School';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SchoolsService {

  constructor(private http: HttpClient) {

  }

  /**
   * Retrieves a school based on the provided email.
   * @param {string} email - The email of the school to retrieve.
   * @returns A HTTP GET request to retrieve the school with the specified email.
   */
  public getSchool(email: string) {
    return this.http.get<School>(`http://localhost:8083/api/colegios/${email}`);
  }

  /**
   * Retrieves a list of schools from the API.
   * @returns An Observable that emits an array of School objects.
   */
  public getSchools() {
    return this.http.get<School[]>('http://127.0.0.1:8083/api/colegios')
  }

  /**
   * Creates a new school.
   * @param {number} id - The ID of the school.
   * @param school - The school object containing the details.
   * @returns A Promise that resolves to the response from the API.
   */
  public newSchool(id: number, school: any) {
    const URL = 'http://127.0.0.1:8083/api/colegios';
    let body = school;
    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.post(URL, body);
  }

  /**
   * Deletes a school with the specified email.
   * @param {string} email - The email of the school to delete.
   * @returns A promise that emits the response from the server.
   */
  public deleteSchool(id: number) {
    return this.http.delete(`http://localhost:8083/api/colegios/${id}`);
  }

  /**
   * Activates a school with the specified email.
   * @param {string} email - The email of the school to activate.
   * @returns An Observable that emits the response from the API.
   */
  public activateSchool(id: number): Observable<any> {
    return this.http.put(`http://localhost:8083/api/colegios/reactivate/${id}`, null);
  }

  /**
   * Deactivates a school with the specified email.
   * @param {string} email - The email of the school to deactivate.
   * @returns A Promise that resolves when the deactivation is successful.
   */
  public deactivateSchool(email: string) {
    return this.http.put(`http://localhost:8083/api/colegios/deactivate/${email}`, null);
  }

  /**
   * Adds a new activity for a given email.
   * @param {string} email - The email of the school.
   * @param activity - The activity to be added.
   * @returns A Promise that resolves to the HTTP response from the server.
   */
  public addNewActivity(email: string, activity: any) {
    return this.http.post(`http://localhost:8083/api/colegios/${email}/actividades`, activity);
  }

  /**
   * Adds an existing activity to a school.
   * @param {string} email - The email of the school.
   * @param {number} activityId - The ID of the activity to be added.
   * @returns A Promise that emits the response from the server.
   */
  public addExistingActivity(email: string, activityId: number) {
    return this.http.post(`http://localhost:8083/api/colegios/${email}/actividades/${activityId}`, null);
  }

  /**
   * Modifies the description of a school.
   * @param {string} email - The email of the school.
   * @param {string} description - The new description for the school.
   * @returns A promise that emits the response from the server.
   */
  public modifyDescription(email: string, description: string) {
    const data = { description: description };
    return this.http.put(`http://localhost:8083/api/colegios/descripcion/${email}`, data);
  }
}
