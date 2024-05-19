import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { jwtDecode } from 'jwt-decode';
import { Router } from '@angular/router';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  private token: any = sessionStorage.getItem('Token');
  private decodedToken: any;
  public userExists: boolean = false;

  constructor(private authService: AuthService,
    private router: Router
    ){
      this.getToken();
    }

    ngOnInit(){
      this.getToken();
    }

    

    /**
     * Retrieves the token and sets the userExists flag to true if the token exists.
     */
    getToken(){
      if(this.token){
        this.userExists = true;
      }
    }

    /**
     * Logs out the user with logout method of the authService.
     * Deletes the user's token from the session storage.
     */
    exit(){
      this.authService.logout();
    }

    /**
     * Navigates to a specific route in private-area based on the user's token.
     */
    go(){
     const token = sessionStorage.getItem('Token');
     if(token){
       const decodedToken: any = jwtDecode(token);
       console.log('Rol en el boton '+decodedToken.rol);
       this.router.navigate(['/private-area/'+decodedToken.rol]);

     }else{
      this.router.navigate(['home']);
     }
    }
}
