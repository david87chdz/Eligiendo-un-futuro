import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import Swal from 'sweetalert2';
import * as bcrypt from 'bcryptjs';
import { UsersService } from 'src/app/services/users.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  public loginForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required])
  });

  constructor(private authService: AuthService, private router: Router,
    private usersService: UsersService
  ) { }

  /**
   * Sends a password reminder to the user's email address.
   */
  public passwordReminder() {
    Swal.fire({
      title: 'Ingrese su dirección de correo electrónico:',
      input: 'email',
      inputPlaceholder: 'Correo electrónico',
      showCancelButton: true,
      confirmButtonText: 'Enviar',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.usersService.forgotPassword(result.value).subscribe(
          (response) => {
            Swal.fire('Correo electrónico enviado', 'Se ha enviado un correo electrónico con las instrucciones para restablecer la contraseña', 'success');
          },
          (error) => {
            console.error('Error:', error);
            if (error.status === 404) {
              Swal.fire('Error', 'El correo electrónico no se encontró en la base de datos', 'error');
            } else {
              Swal.fire('Error', 'Error de conexíon con el servidor intentalo de nuevo en unos segundos', 'error');
            }
          }
        );
      }
    });

  }


  /**
   * Authenticates the user with the provided email and password.
   * If the authentication is successful, the user is redirected to the 'schools' page.
   * If the user is deactivated, a confirmation dialog is shown to reactivate the user.
   * If the user is not found or the password is incorrect, an error message is shown.
   */
  public login(): void {
    if (this.loginForm.invalid) {
      if (this.loginForm.controls['email'].invalid) {
        Swal.fire('Error', 'El email tiene que tener un formato válido', 'error');
      }
      if (this.loginForm.controls['password'].invalid) {
        Swal.fire('Error','El campo de contraseña no puede estar vacío');
      }
      return;
    }
    this.authService.findUser(this.loginForm.value.email, this.loginForm.value.password).subscribe(
      (response) => {

        sessionStorage.setItem('user', JSON.stringify(response));
        this.router.navigate(['schools']);
      }, (error) => {
        Swal.fire({
          icon: 'error', title: 'Error', text: 'Usuario o contraseña incorrectos'
        }).then(() => {
          Swal.fire({
            title: '¿Nuevo usuario?',
            text: '¿Quieres registrarte?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: '¡Sí!',
            cancelButtonText: 'No, gracias'
          }).then((result: any) => {
            if (result.value) {
              Swal.fire(
                '¡Genial!',
                'Te redirigimos.',
                'success'
              )
              this.router.navigate(['/signup']);
            } else if (result.dismiss === Swal.DismissReason.cancel) {
              Swal.fire(
                'De acuerdo',
                'Vuelve pronto!',
                'error'
              )
              this.router.navigate(['/home']);
            }
          });
        });
      });
  }
}
