import { Injectable } from '@angular/core';
import { User } from '../interfaces/User';
import { BehaviorSubject, Observable, catchError, throwError } from 'rxjs';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { json } from 'express';
import * as e from 'express';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};
@Injectable({
  providedIn: 'root',
})
export class UsersService {
  constructor(private http: HttpClient) { }

  /**
   * Retrieves a list of users from the server.
   * @returns {Observable<User[]>} An observable that emits the list of users.
   */
  public getUsers() {
    return this.http.get<User[]>(`http://localhost:8083/api/usuarios`);
  }

  /**
   * Creates a new user.
   * @param user - The user object to be created.
   * @returns A Promise that emits the response from the server.
   */
  public createUser(user: Object) {
    let userJson = JSON.stringify(user);
    return this.http.post(
      `http://localhost:8083/api/usuarios`,
      userJson,
      httpOptions
    );
  }


  /**
   * Updates a user with the specified ID.
   * @param {string} id - The ID of the user to update.
   * @param {Object} user - The updated user object.
   * @returns {Observable<any>} - An observable.
   */
  updateUser(id: string, user: Object) {
    let userJson = JSON.stringify(user);
    return this.http.put(
      `http://localhost:8083/api/usuarios/${id}`,
      userJson,
      httpOptions
    );
  }

  /**
   * Retrieves new users from the server.
   * @returns An Observable with array of users that emits the response from the server.
   */
  public getNewUsers(): Observable<any> {
    return this.http.get('http://localhost:8083/api/usuarios/newUsers');
  }

  /**
   * Sends a password reset email to the specified email address.
   * @param {string} email - The email address to send the password reset email to.
   * @returns An Observable that resolves to the response from the server.
   */
  public forgotPassword(email: string): Observable<any> {
    const url = 'http://localhost:3000/send-password-email';
    return this.http.post<any>(url, { email });
  }


  /**
   * Creates a new school user.
   * @param user - The user object to be created.
   * @returns A Promise that emits the response from the server.
   */
  public newSchoolUser(user: Object) {
    return this.http.post(
      `http://localhost:3000/schoool-registry`,
      user,
      httpOptions
    );
  }

  /**
   * Checks if a school exists for the given email.
   * @param email - The email to check.
   * @returns A Promise that resolves with the response from the server.
   */
  public noExistsSchool(email: string) {
    return this.http.post(`http://localhost:3000/school-no-exists`, { email });
  }

  /**
   * Retrieves a user by their ID.
   * @param id - The ID of the user to retrieve.
   * @returns A Promise that resolves to the user object.
   */
  public getUser(id: number) {
    return this.http.get(`http://localhost:8083/api/usuarios/${id}`);
  }
}
