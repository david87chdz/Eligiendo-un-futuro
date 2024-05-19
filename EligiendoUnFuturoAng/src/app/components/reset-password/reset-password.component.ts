import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import * as bcrypt from 'bcryptjs';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent {

  public resetPasswordForm: FormGroup;
  private token!: string;
  
  constructor(private http: HttpClient,
     private route: ActivatedRoute,
    private router: Router
    ) {
    this.resetPasswordForm = new FormGroup({
      password: new FormControl('', Validators.required),
      confirmPassword: new FormControl('', Validators.required)
    });
    this.route.params.subscribe(params => {
      this.token = params['token'];
      console.log('Token:', this.token);
    }
    );
  }

  /**
   * Resets the user's password.
   * This method validates the reset password form, checks if the passwords match,
   * and sends a POST request to reset the password
   * If the password reset is successful, it displays a success message
   * and navigates the user to the home page.
   */
  resetPassword() {
    if (this.resetPasswordForm.valid) {
      const token = this.token;
      console.log('Token:', token);
      const passwordControl = this.resetPasswordForm.get('password');
      console.log('Password Control:', passwordControl);
      const confirmPasswordControl = this.resetPasswordForm.get('confirmPassword');
      const confirmPassword = confirmPasswordControl ? confirmPasswordControl.value : '';
  
      if (passwordControl && passwordControl.value !== confirmPassword) {
        Swal.fire('Error', 'Las contraseñas no coinciden', 'error');
        return;
      }
  
      let password = passwordControl ? passwordControl.value : '';
      password=bcrypt.hashSync(password, 10)
      console.log('Password:', password);
  
      this.http.post(`http://localhost:3000/reset-password`, { token, password })
        .subscribe(response => {
          Swal.fire('Contraseña restablecida', 'La contraseña se ha restablecido correctamente', 'success');
          this.router.navigate(['/home']);
        }, error => {
          if (error.status === 406) {
            Swal.fire('Error', 'El tiempo de reestablecimiento de contraseña ha caducado solicita recordar contraseña de nuevo', 'error');
          }else if (error.status === 400) {
          Swal.fire('Error', 'Conexión con el servidor caducado', 'error');
          }
        }
      );
    } else {
      Swal.fire('Error', 'Los campos de contraseña no pueden estar vacíos', 'error');
    }
  }
}
