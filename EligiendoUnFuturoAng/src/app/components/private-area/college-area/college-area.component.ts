import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgModel } from '@angular/forms';
import { jwtDecode } from 'jwt-decode';
import { catchError, map, of } from 'rxjs';
import { School } from 'src/app/interfaces/School';
import { SchoolsService } from 'src/app/services/schools.service';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { FileUpload, UploadEvent } from 'primeng/fileupload';
import { ImagesService } from 'src/app/services/images.service';
import Swal from 'sweetalert2';
import { CommentsService } from 'src/app/services/comments.service';
import { Comments } from 'src/app/interfaces/Comment';
import { Answers } from 'src/app/interfaces/Answers';
import { AnswersService } from 'src/app/services/answers.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Activities } from 'src/app/interfaces/Activities';
import { ActivitiesService } from 'src/app/services/activities.service';
import { AuthService } from 'src/app/services/auth.service';
import { CordinatesService } from 'src/app/services/cordinates.service';
import { Images } from 'src/app/interfaces/Images';
import { User } from 'src/app/interfaces/User';
import { UsersService } from 'src/app/services/users.service';
import { UpperCasePipe } from '@angular/common';
import * as bcrypt from 'bcryptjs';
import { Password } from 'primeng/password';
@Component({
  selector: 'app-college-area',
  templateUrl: './college-area.component.html',
  styleUrls: ['./college-area.component.scss'],
  providers: [MessageService],
})
export class CollegeAreaComponent implements OnInit {

  public school: School | null = null;
  public change: boolean = false;
  public newImage: boolean = false;
  public activityOption: 'existing' | 'new' | undefined;

  public typeOptions: any[] = [
    { label: 'Optativa', value: 'Optativa' },
    { label: 'Extraescolar', value: 'Extraescolar' }
  ];

  public email: string | null = null;

  private id: number | undefined;
  public comments: Comments[] = [];
  public answers: Answers[] = [];

  public dialog = false;
  public modifying = false;
  public comentSelected!: Comments;
  public newAnswer = '';
  public addActivity = false;
  public viewComents = false;
  public form!: FormGroup;
  public foto: File | null = null;
  public newActivity: any = {
    name: null,
    tipe: null
  };


  public activities: Activities[] = [];
  public activitiesSchool: Activities[] = [];
  public miLaitud: number = 0;
  public miLongitud: number = 0;

  public changepassword: boolean = false;
  public registry: boolean = true;

  public answerToModify: any;
  public images: any[] = [];
  public user: User | null = null;
  public newPassword: any;
  public confirmPassword: any;
  public loader: boolean = false;

  constructor(
    private schoolService: SchoolsService,
    private cd: ChangeDetectorRef,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private commentService: CommentsService,
    private imagesService: ImagesService,
    private answerService: AnswersService,
    private activityService: ActivitiesService,
    private authService: AuthService,
    private cordinatesService: CordinatesService,
    private userService: UsersService
  ) {
    this.id = (jwtDecode(sessionStorage.getItem('Token') || '') as any)?.id;
    this.email = (jwtDecode(sessionStorage.getItem('Token') || '') as any)?.email;
    this.getSchool(this.email || '');
    this.getSchools();
    this.getComments(this.school?.id || 0);
    this.getActivities();
    this.getCordinatesClient();

    this.userService.getUser(this.id || 0).subscribe(
      (user: any) => {
        this.user = user;
      },
      (error) => {
        Swal.fire('Error', 'Error de conexión en el servidor', 'error');
      }
    );

  }
  ngOnInit(): void { }

  /**
   * Retrieves the school information based on the provided email.
   * @param email - The email of the school to retrieve.
   */
  getSchool(email: string): void {
    this.schoolService.getSchool(email).subscribe(
      (response) => {
        this.school = response;
        this.getAllImages();
        this.form = this.formBuilder.group({
          descripcion: [this.school?.descripcion || ''],
        });
      },
    );
  }

  /**
   * Retrieves the schools and find the school with the same email with user school email.
   */
  getSchools(): void {
    this.schoolService
      .getSchools()
      .pipe(
        map((schools: School[]) => {
          return schools.find((school) => school.email === this.email) || null;
        }),
        catchError((error: any) => {
          Swal.fire('Error', 'Error de conexión en el servidor', 'error');
          return of(null);
        })
      )
      .subscribe((filteredSchool: School | null) => {
        if (filteredSchool !== null) {
          this.school = filteredSchool;
          this.getComments(this.school.id);
          /* this.getCordinatesSchool(this.school.codigo); */
        }
      });
  }

  /**
   * Retrieves all activities.
   * Also calls the 'getActivitiesBySchool' method.
   */
  getActivities(): void {
    this.activityService.getAllActivities().subscribe(
      (response) => {
        this.activities = response;
        this.getActivitiesBySchool();
      },
      (error) => {
        Swal.fire('Error', 'Error de conexión en el servidor', 'error');
      }
    );
  }

  /**
   * Toggles the visibility of the form.
   */
  showForm() {
    this.change = !this.change;
  }


  /**
   * Toggles the state of `newImage` property.
   */
  addImage() {
    this.newImage = !this.newImage;
  }

  /**
   * Retrieves comments for a given school ID.
   * @param id - The ID of the school.
   */
  getComments(id: number) {
    this.commentService.getCommentsSchoolId(id).subscribe(
      (response) => {
        this.comments = response;
        //this.getAnswerComments();
        this.getAnswerComments();
      }
    );
  }


  /**
   * Retrieves the answer comments from the answer service.
   * Subscribes to the answer service's getAnswers.
   */
  getAnswerComments(): void {
    this.answerService.getAnswers().subscribe(
      (answer: any) => {
        this.answers = answer;
      }
    );
  }





  /**
   * Saves the answer for a selected comment.
   */
  saveAnswer(): void {
    this.answerService
      .saveAnswer(this.comentSelected.id, this.newAnswer, this.school?.id ?? 0)
      .subscribe(
        () => {
          this.getComments(this.school?.id || 0);
          this.dialog = false;
          this.messageService.add({
            severity: 'success',
            summary: 'Respuesta guardada',
            detail: 'La respuesta ha sido guardada con éxito.',
          });
        },
        (error) => {

          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Hubo un error al guardar la respuesta.',
          });
        }
      );
  }

  /**
   * Modifies an answer with the specified answerId and newContent.
   * @param answerId - The ID of the answer to modify.
   * @param newContent - The new content for the answer.
   */
  modifyAnswer(answerId: any, newContent: string): void {
    this.answerService.updateAnswer(answerId, newContent).subscribe(
      () => {
        Swal.fire({
          title: 'Respuesta modificada',
          text: 'La respuesta ha sido modificada con éxito.',
          icon: 'success',
          confirmButtonText: '¡De acuerdo!',
        });
        this.getComments(this.school?.id || 0);
      },
      (error) => {
        Swal.fire({
          title: 'Error',
          text: 'Ha ocurrido un error al modificar la respuesta.',
          icon: 'error',
          confirmButtonText: '¡De acuerdo!',
        });
      }
    );
  }

  closeEditComment(): void {
    this.dialog = false;
  }

  /**
   * Checks if an answer exists for a given comentarioId.
   * @param comentarioId - The ID of the comentario to check for an answer.
   * @returns A boolean true or false if an answer exists for the given comentarioId.
   */
  answerExists(comentarioId: number): boolean {
    return this.answers.some((respuesta) => {
      return (
        respuesta.comentario.id === comentarioId
      );
    });
  }

  /**
   * Retrieves the content of an answer based on the comment ID.
   * @param commentId - The ID of the comment.
   * @returns The answer content if found, or an empty Answers object.
   */
  getAnswerContent(commentId: number): Answers {
    const answer = this.answers.find(
      (answer) => answer.comentario.id === commentId
    );
    return answer || ({} as Answers);
  }

  /**
   * Deletes an answer with the specified answerId.
   * @param answerId - The ID of the answer to delete.
   */
  deleteAnswer(answerId: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: "No podrás revertir esto!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '¡Sí, bórralo!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.answerService.deleteAnswer(answerId).subscribe(
          () => {
            this.messageService.add({
              severity: 'success',
              summary: 'Respuesta eliminada',
              detail: 'La respuesta ha sido eliminada con éxito.',
            });
            this.getComments(this.school?.id || 0);
          },
          (error) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Hubo un error al eliminar la respuesta.',
            });
          }
        );
      }
    })
  }

  /**
   * Toggles the value of the addActivity.
   */
  newActivityAdd() {
    this.addActivity = !this.addActivity;
  }

  /**
   * Adds a new activity.
   */
  addNewActivity() {
    this.newActivity.tipe = this.newActivity.tipe.toUpperCase();
    this.newActivity.name = this.newActivity.name.toUpperCase();
    this.schoolService
      .addNewActivity(this.email || '', this.newActivity)
      .subscribe(
        (response) => {
          if (response)
            Swal.fire({
              title: 'Actividad añadida',
              text: 'La actividad ha sido añadida con éxito.',
              icon: 'success',
              confirmButtonText: '¡De acuerdo!',
            });
          this.getActivitiesBySchool();
          this.addActivity = false;
        },
        (error) => {
          if (error.status === 409) {
            Swal.fire({
              title: 'Error',
              text: 'Esta actividad ya existe en la lista de actividades.',
              icon: 'error',
              confirmButtonText: '¡De acuerdo!',
            });
          } else {
            Swal.fire({
              title: 'Error',
              text: 'Ha ocurrido un error al añadir la actividad.',
              icon: 'error',
              confirmButtonText: '¡De acuerdo!',
            });
          }
        }
      );
  }

  /**
   * Adds an existing activity to the school.
   */
  addExistingActivity() {
    this.schoolService
      .addExistingActivity(this.email || '', this.newActivity)
      .subscribe(
        (response) => {
          Swal.fire({
            title: 'Actividad añadida',
            text: 'La actividad ha sido añadida con éxito.',
            icon: 'success',
            confirmButtonText: '¡De acuerdo!',
          });
          this.getActivitiesBySchool();
          this.addActivity = false;
        },
        (error) => {
          if (error.status === 409) {
            Swal.fire({
              title: 'La actividad no se puede añadir',
              text: 'Esta actividad ya existe en la lista de actividades.',
              icon: 'error',
              confirmButtonText: '¡De acuerdo!',
            });
          } else {
            Swal.fire({
              title: 'Error',
              text: 'Ha ocurrido un error al añadir la actividad.',
              icon: 'error',
              confirmButtonText: '¡De acuerdo!',
            });
          }
        }
      );
  }

  /**
   * Toggles the visibility of comments.
   */
  commentsAndResponse() {
    this.viewComents = !this.viewComents;
  }

  /**
   * Modifies the description of the college.
   */
  modifyDescription() {
    this.schoolService
      .modifyDescription(
        this.email || '',
        this.form.value.descripcion
      )
      .subscribe(
        (response) => {
          Swal.fire({
            title: 'Descripción modificada',
            text: 'La descripción ha sido modificada con éxito.',
            icon: 'success',
            confirmButtonText: '¡De acuerdo!',
          });
          this.change = !this.change;
        },
        (error) => {
          Swal.fire({
            title: 'Error',
            text: 'Ha ocurrido un error al modificar la descripción.',
            icon: 'error',
            confirmButtonText: '¡De acuerdo!',
          });
        }
      );
  }

  /**
   * Deletes the school account.
   * Displays a confirmation dialog to the user before deleting the account.
   * If the user confirms, the account is deleted on the server and the user is logged out.
   */
  deleteSchool() {
    Swal.fire({
      title: '¿Estás seguro de que deseas eliminar tu cuenta?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.schoolService
          .deactivateSchool(this.email || '')
          .pipe(
            catchError((error) => {
              return of(error);
            })
          )
          .subscribe(
            (response) => {
              Swal.fire({
                title: 'Usuario eliminado',
                text: 'El usuario ha sido eliminado con éxito.',
                icon: 'success',
                confirmButtonText: '¡De acuerdo!',
              });
              this.authService.logout();
            },
            (error) => {
              Swal.fire({
                title: 'Error',
                text: 'Ha ocurrido un error al eliminar el usuario.',
                icon: 'error',
                confirmButtonText: '¡De acuerdo!',
              });
            }
          );
      }
    });
  }










  getCordinatesClient() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.miLaitud = position.coords.latitude;
        this.miLongitud = position.coords.longitude;
      });
    }
  }

  /**
   * Retrieves the activities associated with the school.
   */
  getActivitiesBySchool() {
    this.activityService
      .getActivitiesBySchoolId(this.school?.id || 0)
      .subscribe(
        (response) => {
          this.activitiesSchool = response;
          this.loader = true;
        }
      );
  }

  /**
   * Deletes an activity from the school.
   * @param id - The ID of the activity to be deleted.
   */
  deleteActivity(id: number) {
    if (this.school && this.school.id) {
      this.activityService.deleteActivityFromSchool(this.school.id, id).subscribe(() => {
      });
    }
    Swal.fire({
      title: '¿Estás seguro de que deseas eliminar esta actividad?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
    }).then(
      (result) => {
        if (result.isConfirmed) {

          this.messageService.add({
            severity: 'success',
            summary: 'Actividad eliminada',
            detail: 'La actividad ha sido eliminada con éxito.',
          });
        }
        this.getActivitiesBySchool();
      },
      (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Hubo un error al eliminar la actividad.',
        });
      }
    );
  };

  /**
   * Retrieves all images if are active for the user's college.
   */
  getAllImages() {
    this.imagesService.getImages().subscribe(
      (response) => {
        this.images = response.filter((image: Images) => image.colegio.email === this.email && image.activo === true);
      },
      (error) => {
        Swal.fire('Error', 'Error de conexión en el servidor', 'error');
      }
    );
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
        }
        Swal.fire('Imagen reactivada', 'La imagen ha sido reactivada correctamente', 'success');
      },
      (error: any) => {
        Swal.fire('Error', 'Fallo de conexion con el servidor intentelo en unos segundos', 'error');
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
      }
    );
    this.getAllImages();
  }

  /**
   * Toggles the change password flag.
   */
  public changePassword() {
    this.changepassword = !this.changepassword;
  }

  /**
   * Change the password by the user.
   */
  public saveChanges() {
    let userChanges = {
      password: '',
    };

    if (this.newPassword || this.confirmPassword) {
      if (this.newPassword !== this.confirmPassword) {
        Swal.fire('Error', 'Las contraseñas no coinciden', 'error');
        return;
      }
      userChanges.password = bcrypt.hashSync(this.newPassword, 10);
    }

    this.userService.updateUser(this.user?.id.toString() ?? '', userChanges).subscribe(
      (response) => {
        this.changepassword = !this.changepassword;
      },
      (error) => {
        Swal.fire('Error', 'Error de conexión en el servidor', 'error');
      }
    );
  }
  

  /**
   * Handles the event when a new photo is inserted.
   * @param event - The event object containing information about the inserted photo.
   */
  newPhotoInsert(event: any) {
    const file = event.target.files[0];
    if (!file.type.match('image/jpeg') && !file.type.match('image/png')) {
      Swal.fire({
        title: 'Formato no válido',
        text: 'El archivo que intentas subir no es una imagen en formato .jpg o .png. Por favor, intenta con otro archivo.',
        icon: 'warning',
        confirmButtonText: '¡De acuerdo!'
      });
      event.target.value = ''
      return;
    }

    this.foto = file; // Guarda el objeto File en this.foto
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {

    }
  }

  newPhoto() {
    if (this.foto == null) {
      // Maneja el caso en que no se ha seleccionado un archivo
      return;
    }
    this.imagesService.uploadImageFile(this.foto, this.school?.email).subscribe(
      (response) => {
        Swal.fire({
          title: 'Imagen subida',
          text: 'La imagen ha sido subida con éxito.',
          icon: 'success',
          confirmButtonText: '¡De acuerdo!',
        });
        window.location.reload();
      },
      (error) => {
        console.error(error);
        if (error.status === 0) {
          Swal.fire({
            title: 'Archivo demasiado grande',
            text: 'El archivo que intentas subir es demasiado grande. Por favor, intenta con un archivo más pequeño.',
            icon: 'warning',
            confirmButtonText: '¡De acuerdo!'
          })
        } else if (error.status === 409) {
          Swal.fire({
            title: 'Error',
            text: 'Ya existe esa imagen con ese nombre, por favor cambie el nombre de la imagen',
            icon: 'warning',
            confirmButtonText: '¡De acuerdo!'
          })
        }
        else {
          Swal.fire({
            title: 'Actualizacion no realizada',
            text: 'Ha habido un problema en la actualizacion intentelo de nuevo mas tarde',
            icon: 'warning',
            confirmButtonText: '¡De acuerdo!'
          })
        }
      }
    );
  }
}


