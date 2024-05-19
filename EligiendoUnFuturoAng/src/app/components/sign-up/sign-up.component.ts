import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { UsersService } from 'src/app/services/users.service';
import * as bcrypt from 'bcryptjs';
import Swal from 'sweetalert2';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent {
  constructor(private userService: UsersService,
    private authService: AuthService,
    private router: Router
  ) { }
  public form = new FormGroup({
    name: new FormControl('', Validators.required),
    surname: new FormControl(''),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required),
    passwordConfirm: new FormControl('', Validators.required),
    rol: new FormControl('', Validators.required)
  });

  public roles: string[] = ['User', 'School'];


  /**
   * Submits the form data for user registration.
   * @remarks
   * This method validates the form data and performs the necessary actions based on the user's role.
   * If the user is registering as a school, it prompts for confirmation of the official email address.
   * If the form data is valid, it calls the `loginUser` method to complete the registration process.
   * @returns void
   */
  submit() {
    let plainPassword = this.form.value.password;
    let confirmPassword = this.form.value.passwordConfirm;
    let user = {
      nombre: this.form.value.name,
      apellidos: this.form.value.surname,
      email: this.form.value.email,
      password: plainPassword ? bcrypt.hashSync(plainPassword, 10) : '',
      rol: this.form.value.rol
    }

    if (!user.nombre || !user.rol || !user.email || !plainPassword || !confirmPassword) {
      Swal.fire({ icon: 'error', title: 'Error', text: 'Faltan campos obligatorios' });
      return;
    }

    const emailRegex = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;
    if (!emailRegex.test(user.email)) {
      Swal.fire({ icon: 'error', title: 'Error', text: 'El correo electrónico no tiene un formato válido' });
      return;
    }

    if (plainPassword !== confirmPassword) {
      Swal.fire({ icon: 'error', title: 'Error', text: 'Las contraseñas no coinciden' });
      return;
    }
    if (user.rol == 'School') {
      Swal.fire({
        title: 'Registro como colegio',
        text: 'Tu email debe ser el email oficial del colegio. ¿Es correcto?',
        icon: 'info',
        showCancelButton: true,
        confirmButtonText: 'Sí, lo es',
        cancelButtonText: 'No, gracias',
        input: 'email',
        inputValue: user.email
      }).then((result) => {
        if (result.isConfirmed) {
          user.email = result.value;
          this.userService.newSchoolUser({ email: user.email, name: user.nombre }).subscribe(
            response => {
              console.log('user registry: ', user.email, user.password);
              if (typeof plainPassword === 'string') {
                this.loginUser(user, plainPassword);
              }
            }, error => {
              console.log('Error al registrar usuario', error);
            }
          )
        } else {
          this.form.controls['email'].setValue('');
        }
      });
    } else {
      this.loginUser(user, plainPassword);
    }
  }

  /**
   * Logs in a user by creating a new user and then finding the user by email and password.
   * If successful, it sets the user token in the session storage and navigates to the schools page.
   * If unsuccessful, it displays an error message and navigates to the home page.
   * @param user - The user object to be created.
   * @param plainPassword - The plain password of the user.
   */
  loginUser(user: any, plainPassword: string) {
    this.userService.createUser(user).subscribe(
      response => {
        console.log('user registry: ', user.email, user.password);
        Swal.fire({ icon: 'success', title: 'Usuario creado', text: 'Usuario creado correctamente' });
        this.authService.findUser(user.email, plainPassword).subscribe(
          response => {
            Swal.fire({ icon: 'success', title: 'Usuario logueado', text: 'Usuario logueado correctamente Bienvenid@' });
            sessionStorage.setItem('Token', response.token);
            this.router.navigate(['/schools']);
          },
          error => {
            Swal.fire({ icon: 'error', title: 'Error', text: 'Error al logear usuario' });
            this.router.navigate(['/home']);
          }
        );
      },
      error => {
        console.log('Error al registrar usuario', error);
        if (error.status == 409) {
          Swal.fire({ icon: 'error', title: 'Error', text: 'Ese email esta en uso' });
          this.router.navigate(['/home']);
        } else {
          Swal.fire({ icon: 'error', title: 'Error', text: 'Error al registrar usuario' });
        }
      }
    );
  }
}