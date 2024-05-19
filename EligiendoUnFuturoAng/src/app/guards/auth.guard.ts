import { Injectable } from '@angular/core';
import { CanActivateFn, CanLoad, Route, Router, UrlSegment } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanLoad {
  constructor(private router: Router) {}
  canLoad(route: Route, segments: UrlSegment[]): boolean {
    const token = sessionStorage.getItem('Token');
    if (token) {
      try {
        const decodedToken: any = jwtDecode(token);
        if (decodedToken && decodedToken.rol) {
          if(decodedToken.rol === 'Admin' || decodedToken.rol === 'User' || decodedToken.rol === 'School'){
          return true;
          }else{
            this.router.navigate(['/home']);
            return false;
          }
        }
      } catch (error) {
        console.error('Error al decodificar el token:', error);
      }
    }
    this.router.navigate(['/home']);
    return false;
  }
}