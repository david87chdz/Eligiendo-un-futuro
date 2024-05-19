import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { User } from '../interfaces/User';
import { tap } from 'rxjs/operators';
import { jwtDecode } from 'jwt-decode';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  user!: User;

  private loginUser: BehaviorSubject<User | null>;



  constructor(private http: HttpClient,
    private router: Router
  ) {
    this.loginUser = new BehaviorSubject<any>(null);
  }



  /**
   * Finds a user by email and password.
   * @param email - The email of the user.
   * @param password - The password of the user.
   * @returns An Observable that emits the response from the server.
   */
  findUser(email: string, password: string): Observable<any> {
    const requestBody = { email, password };
    return this.http.post<any>('http://localhost:3000/login', requestBody).pipe(
      tap((response: User) => {
        if (response && response.token) {
          sessionStorage.setItem('Token', response.token);
          const decodedToken: any = jwtDecode(response.token);
          this.user = {
            email: decodedToken.email, rol: decodedToken.rol, id: response.id, nombre: response.nombre, apellidos: response.apellidos,
            password: decodedToken.password, token: response.token
          };
          this.setUser(this.user);
        }
      })
    );
  }

  /**
   * Retrieves the user from the `loginUser` subject.
   * @returns An Observable that emits the user.
   */
  getUser(): Observable<User | null> {
    return this.loginUser.asObservable();
  }


  public setUser(user: User) {
    this.loginUser.next(user);
    //localStorage.setItem('Rol', user.rol); 
  }


  /**
   * Logs out the user by clearing the session storage, emitting a null value through the `loginUser` subject,
   * and navigating to the home page.
   */
  logout(): void {
    sessionStorage.clear();
    this.loginUser.next(null);
    window.location.href = '/home'
  }


  /**
   * Reactivates a user by sending a PUT request to the server.
   * @param {string} email - The email of the user to reactivate.
   * @returns An Observable that emits the response from the server.
   */
  reactivateUser(id: number): Observable<any> {
    return this.http.put(`http://localhost:8083/api/usuarios/reactivate/${id}`, {});
  }


  /**
   * Deactivates a user with the specified email.
   * @param {string} email - The email of the user to deactivate.
   * @returns An Observable that emits the response from the server.
   */
  deactivateUser(email: string): Observable<any> {
    return this.http.put(`http://localhost:8083/api/usuarios/deactivate/${email}`, {});
  }


  /**
   * Deletes a user by their ID.
   * @param {number} id - The ID of the user to delete.
   * @returns An Observable that emits the response from the server.
   */
  deleteUser(id: number): Observable<any> {
    return this.http.delete(`http://localhost:8083/api/usuarios/delete/${id}`);
  }


}

