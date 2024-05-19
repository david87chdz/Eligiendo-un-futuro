import { CanActivateFn } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

export const schoolGuard: CanActivateFn = (route, state) => {
  const token = sessionStorage.getItem('Token');


  if (token) {
    try {
      const decodedToken: any = jwtDecode(token);
  
      if (decodedToken && decodedToken.rol) {
        if (decodedToken.role === 'School') {
          return true; 
        }
        return true;
      } else {
        return false; 
      }
    } catch (error) {
      console.error('Error al decodificar el token:', error);
      return false; 
    }
  } else {
    return false; 
  }
};
