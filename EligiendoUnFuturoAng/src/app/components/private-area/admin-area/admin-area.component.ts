import { ChangeDetectorRef, Component } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { catchError, filter, map, of } from 'rxjs';
import { Answers } from 'src/app/interfaces/Answers';
import { Comments } from 'src/app/interfaces/Comment';
import { School } from 'src/app/interfaces/School';
import { User } from 'src/app/interfaces/User';
import { AnswersService } from 'src/app/services/answers.service';
import { ApijcylService } from 'src/app/services/apijcyl.service';
import { CommentsService } from 'src/app/services/comments.service';
import { SchoolsService } from 'src/app/services/schools.service';
import { UsersService } from 'src/app/services/users.service';
import { Observable, BehaviorSubject } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import Swal from 'sweetalert2';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Images } from 'src/app/interfaces/Images';
import { ImagesService } from 'src/app/services/images.service';

@Component({
  selector: 'app-admin-area',
  templateUrl: './admin-area.component.html',
  styleUrls: ['./admin-area.component.scss'],
})
export class AdminAreaComponent {


  public schools: School[] = [];
  public usersNew: Observable<any[]> = of([]);
  private id: number | undefined;
  private email: string | null = null;
  public comments: Comments[] = [];
  public answers: Answers[] = [];
  public users: User[] = [];
  public newSchool: any;
  public activities: any[] = [];
  public images: Images[] = [];


  constructor(
    private schoolService: SchoolsService,
    private cd: ChangeDetectorRef,
    private userService: UsersService,
    private commentService: CommentsService,
    private answerService: AnswersService,
    private apiService: ApijcylService,
    private authService: AuthService,
    private imagesService: ImagesService
  ) {
    this.id = (jwtDecode(sessionStorage.getItem('Token') || '') as any)?.id;
    this.email = (jwtDecode(sessionStorage.getItem('Token') || '') as any)?.email;
    this.getNewUsers();
    this.getComments();
    this.getAnswers();
    this.getSchools();
    this.getUsers();
    this.getImages();
  }

  /**
   * Retrieves users register but wihtout school associated from the user service.
   */
  getNewUsers(): void {
    this.usersNew = this.userService.getNewUsers();
  }

  deleteUser(id: number): void {
    Swal.fire({
      title: '¿Estás seguro de que deseas eliminar este usuario?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.authService.deleteUser(id).subscribe(
          (value: any) => {
            Swal.fire('Usuario eliminado', 'El usuario ha sido eliminado correctamente', 'success');
            this.getNewUsers();
            this.getUsers();
            this.getSchools();
          },
          (error: any) => {
            Swal.fire('Error', 'Error de conexion con el servidor', 'error');
          }
        );
      }
    });

  }

  /**
   * Deactivates a user with the specified email.
   * @param email - The email of the user to deactivate.
   */
  deactivateUser(email: string): void {
    this.authService.deactivateUser(email).subscribe(
      (response: any) => {
        Swal.fire('Usuario desactivado', 'El usuario ha sido desactivado correctamente', 'success');
        this.getUsers();
      },
      (error: any) => {
        Swal.fire('Error', 'Error de conexion con el servidor', 'error');
      }
    );
  }

  /**
   * Reactivates a user with the specified ID.
   * @param id - The ID of the user to reactivate.
   */
  reactivateUser(id: number): void {
    this.authService.reactivateUser(id).subscribe(
      (response: any) => {
        Swal.fire('Usuario reactivado', 'El usuario ha sido reactivado correctamente', 'success');
        this.getUsers();
        this.getImages();
        this.getSchools();
        this.getComments();
      },
      (error: any) => {
        if (error.status === 409) {
          Swal.fire('No es posible', 'Ya hay un usuario activo con ese email', 'error');
        }
        else {
          Swal.fire('Error', 'Error de conexion con el servidor', 'error');
        }
      });
  }

  /**
   * Retrieves the comments from the comment service and filters them based on the active status of the associated school.
   */
  getComments(): void {
    this.commentService.getComments().subscribe(
      (value: Comments[]) => {
        this.comments = value.filter(comment => comment.colegio.activo);
      },
      (error) => {
        Swal.fire('Error', 'Error en el servidor', 'error');
      }
    );
  }

  /**
   * Deletes a comment with the specified ID.
   * @param id - The ID of the comment to delete.
   */
  deleteComment(id: number): void {
    this.commentService.deleteComment(id).subscribe(
      (value: any) => {
        Swal.fire('Comentario eliminado', 'El comentario ha sido eliminado correctamente', 'success');
        this.getComments();
      },
      (error) => {
        Swal.fire('Error', 'Error de conexion con el servidor', 'error');
      }
    );
  }

  /**
   * Reactivates a comment with the specified ID.
   * @param id - The ID of the comment to reactivate.
   */
  reactivateComment(id: number): void {
    this.commentService.reactivateComment(id).subscribe(
      (value: any) => {
        Swal.fire('Comentario reactivado', 'El comentario ha sido reactivado correctamente', 'success');
        this.getComments();
      },
      (error) => {
        Swal.fire('Error', 'Error de conexión con el servidor', 'error');
      }
    );
  }

  /**
   * Deactivates a comment with the specified ID.
   * @param id - The ID of the comment to deactivate.
   */
  deactivateComment(id: number): void {
    this.commentService.deactivateComment(id).subscribe(
      (value: any) => {
        Swal.fire('Comentario desactivado', 'El comentario ha sido desactivado correctamente', 'success');
        this.getComments();
      },
      (error) => {
        Swal.fire('Error', 'Error de conexión con el servidor', 'error');
      }
    );
  }

  /**
   * Retrieves the answers from the answer service.
   */
  getAnswers(): void {
    this.answerService.getAnswers().subscribe(
      (value: Answers[]) => {
        this.answers = value;
      },
      (error) => {
        Swal.fire('Error', 'Error en el servidor', 'error');
      }
    );
  }

  /**
   * Checks if the given comment has an answer.
   * @param comment - The comment to check.
   * @returns A boolean value indicating whether the comment has an answer.
   */
  hasAnswer(comment: any): boolean {
    return this.answers && this.answers.some(answer => answer.comentario.id === comment.id);
  }


  /**
   * Retrieves the answer associated with the given comment.
   * @param comment - The comment to retrieve the answer for.
   * @returns The answer associated with the comment, or null if no answer is found.
   */
  getThisAnswer(comment: any): any {
    return this.answers.find((answer) => {
      if (answer.comentario.id === comment.id) {
        return answer.contenido;
      }
      return null;
    }) || null;
  }


  /**
   * Retrieves the list of schools.
   */
  getSchools(): void {
    this.schoolService.getSchools().subscribe(
      (value: School[]) => {
        this.schools = value
      },
      (error) => {
        Swal.fire('Error', 'Error de conexión en el servidor', 'error');
      }
    );
  }

  /**
   * Reactivates a school with the specified ID.
   * @param id - The ID of the school to reactivate.
   */
  reactivateSchool(id: number): void {
    this.schoolService.activateSchool(id).subscribe(
      (response: any) => {
        if (response.status === 200) {
          Swal.fire('Colegio activado', 'El colegio ha sido activado correctamente', 'success');
          this.getSchools();
        }
      },
      (error: HttpResponse<any>) => {
        if (error.status === 200) {
          Swal.fire('Colegio activado', 'El colegio ha sido activado correctamente', 'success');
          this.getSchools();
        }
      }
    );
  }

  /**
   * Deletes a school with the specified ID.
   * @param id - The ID of the school to delete.
   */
  deleteSchool(id: number): void {
    Swal.fire({
      title: '¿Estás seguro de que deseas eliminar este colegio?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.schoolService.deleteSchool(id).subscribe(
          (response: any) => {
            if (response.status === 200) {
              Swal.fire('Colegio eliminado', 'El colegio ha sido eliminado correctamente', 'success');
            }
          },
          (error: HttpErrorResponse) => {
            if (error.status === 200) {
              Swal.fire('Colegio eliminado', 'El colegio ha sido eliminado correctamente', 'success');
              this.getSchools();
            } else {
              console.error('Error al eliminar la escuela:', error);
            }

          }
        );
      }
      this.getSchools();
      this.getUsers();
      this.getNewUsers();
    });
  }

  /**
   * Deactivates a school with the specific email.
   * @param email - The email of the school to be deactivated.
   */
  deactivateSchool(email: string): void {
    Swal.fire({
      title: '¿Estás seguro de que deseas desactivar este colegio?',
      text: 'Esta acción se puede deshacer',
      icon: 'info',
      showCancelButton: true,
      confirmButtonText: 'Sí',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.schoolService.deactivateSchool(email).subscribe(
          (response: any) => {
            if (response.status === 200) {
              Swal.fire('Colegio desactivado', 'El colegio ha sido desactivado correctamente', 'success');
              this.getSchools();
            }
          },
          (error: HttpErrorResponse) => {
            if (error.status === 200) {
              Swal.fire('Colegio desactivado', 'El colegio ha sido desactivado correctamente', 'success');
              this.getSchools();
            }
          }
        );
      }
    });
  }

  /**
   * Retrieves the list of users from the server.
   */
  getUsers(): void {
    this.userService.getUsers().subscribe(
      (value: User[]) => {
        this.users = value;
      },
      (error) => {
        Swal.fire('Error', 'Error de conexión con el servidor', 'error');
      }
    );
  }


  /**
   * Creates a new school with the provided ID and email.
   * @param id - The ID of the user.
   * @param email - The email of the school.
   */
  createSchool(id: number, email: string): void {
    this.apiService.getAll().pipe(
      map((data: any) => {
        return data.filter((school: any) => school.correo_electronico === email);
      })
    ).subscribe(
      (value: any) => {
        if (value.length === 0) {
          this.userService.noExistsSchool(email).subscribe(
            (response: any) => {
              if (response) {
                Swal.fire({
                  icon: 'error',
                  title: 'Error',
                  html: `Los datos proporcionados no se encuentran dentro de los datos de la junta por favor pongase en contacto con el correo: <a href="mailto:${email}">${email}</a>`,
                });
              }
              (error: HttpErrorResponse) => {
                if (error) {
                  Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    html: `Los datos proporcionados no se encuentran dentro de los datos de la junta por favor pongase en contacto con el correo: <br> <a href="mailto:${email}">${email}</a>`,
                  });
                } else {
                  console.error('Error al crear la escuela:', error);
                }
              }
            }
          );
        } else {
          let schoolFilter = value[0];
          this.newSchool = {
            nombre: schoolFilter.denominacion_especifica,
            direccion: schoolFilter.via + '/ ' + schoolFilter.nombre_de_la_via + ' ' + schoolFilter.numero + ' ' + ' ' + schoolFilter.c_postal,
            localidad: schoolFilter.localidad,
            provincia: schoolFilter.provincia,
            denominacion: schoolFilter.denominacion_generica,
            telefono: schoolFilter.telefono,
            email: schoolFilter.correo_electronico,
            web: schoolFilter.web,
            naturaleza: schoolFilter.naturaleza,
            localizacion: schoolFilter.localizacion.lon + ', ' + schoolFilter.localizacion.lat,
            codigo: schoolFilter.codigo,
            comedor: schoolFilter.comedor === 'S' ? true : false,
            concierto: schoolFilter.concierto === 'S' ? true : false,
          };
          this.schoolService.newSchool(id, this.newSchool).subscribe(
            (response: any) => {
              if (response && response.status === 200) {
                Swal.fire('Colegio creado', 'El colegio ha sido creado correctamente', 'success');
                this.getUsers();
                this.getSchools();
                this.getNewUsers();
              }
            },
            (error: HttpErrorResponse) => {
              if (error && error.status === 200) {
                Swal.fire('Colegio creado', 'El colegio ha sido creado correctamente', 'success');
                this.getUsers();
                this.getSchools();
                this.getNewUsers();
              } else {
                console.error('Error al crear la escuela:', error);

              }
            }
          );
        }
      },
      (error: any) => {
        Swal.fire('Error', 'Ha ocurrido un error de conexion con el servidor', 'error');
      }
    );
  }


  /**
   * Retrieves the images from the images service and filters them based on the active status of the associated school.
   */
  getImages(): void {
    this.imagesService.getImages().subscribe(
      (value: Images[]) => {
        this.images = value.filter(image => image.colegio.activo);
      },
      (error: any) => {
        console.error('Error al obtener las imágenes:', error);
      }
    );
  }

  /**
   * Deletes an image with the specified ID.
   * @param id - The ID of the image to delete.
   */
  deleteImage(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: "No podrás revertir esto!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, bórralo!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.imagesService.deleteImage(id).subscribe(
          (value: any) => {
            Swal.fire('Imagen eliminada', 'La imagen ha sido eliminada correctamente', 'success');
            this.getImages();
          },
          (error: any) => {
            console.error('Error al obtener las escuelas:', error);
          }
        );
      }
    })
  }

  /**
   * Reactivates an image with the specified ID.
   * @param id - The ID of the image to reactivate.
   */
  reactivateImage(id: number): void {
    this.imagesService.reactivateImage(id).subscribe(
      (value: any) => {
        if (value.status === 200) {
          Swal.fire('Imagen reactivada', 'La imagen ha sido reactivada correctamente 200', 'success');
          this.getImages();
        }
        Swal.fire('Imagen reactivada', 'La imagen ha sido reactivada correctamente', 'success');
        this.getImages();
      },
      (error: any) => {
        console.error('Error al obtener las escuelas:', error);
      }
    );
  }

  /**
   * Deactivates an image by its ID.
   * @param id - The ID of the image to deactivate.
   */
  deactivateImage(id: number): void {
    this.imagesService.deactivateImage(id).subscribe(
      (value: any) => {
        Swal.fire('Imagen desactivada', 'La imagen ha sido desactivada correctamente', 'success');
        this.getImages();
      },
      (error: any) => {
        console.error('Error al obtener las escuelas:', error);
      }
    );
  }
}
