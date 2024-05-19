import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/interfaces/User';
import { Observable } from 'rxjs';
import { ButtonModule } from 'primeng/button';
import { jwtDecode } from 'jwt-decode';

import { DialogService } from 'primeng/dynamicdialog';
import Swal from 'sweetalert2';
import { CommentsService } from 'src/app/services/comments.service';
import { Comments } from 'src/app/interfaces/Comment';
import { AnswersService } from 'src/app/services/answers.service';
import { UsersService } from 'src/app/services/users.service';
import { Password } from 'primeng/password';
import { RatingsService } from 'src/app/services/ratings.service';
import * as e from 'express';
import { Evaluations } from 'src/app/interfaces/Evaluations';
import { UpperCasePipe } from '@angular/common';
import * as bcrypt from 'bcryptjs';

@Component({
  selector: 'app-user-area',
  templateUrl: './user-area.component.html',
  styleUrls: ['./user-area.component.scss']
})
export class UserAreaComponent implements OnInit {
  private token = sessionStorage.getItem('Token');
  public user!: User;
  private id!: number;
  public editComment: boolean = false;
  public userChanges = {
    name: this.user?.nombre,
    apellidos: this.user?.apellidos,
    password: this.user?.password
  };
  /*  public editedContent: string= ""; */
  public showEditUser: boolean = false;
  public showevaluations: boolean = false;
  public showcomments: boolean = false;
  public comments: Comments[] = [];
  public commentAnswers: { [id: number]: any[] } = {};
  public newPassword: string = '';
  public confirmPassword: string = '';
  public evaluations: Evaluations[] = [];

  constructor(private authService: AuthService,
    private cdr: ChangeDetectorRef,
    private dialogService: DialogService,
    private commentsService: CommentsService,
    private answerService: AnswersService,
    private userService: UsersService,
    private ratingService: RatingsService) {

    this.id = (jwtDecode(sessionStorage.getItem('Token') || '') as any)?.id;
  }



  ngOnInit(): void {
    if (this.token) {
      const decodedToken: any = jwtDecode(this.token);
      this.userService.getUser(decodedToken.id).subscribe(
        (user: any) => {
          this.user = user;

        },
        (error) => {
          Swal.fire('Error', 'Error de conexión en el servidor', 'error');
        }
      );
    }

  }




  /**
   * Deletes the user account.
   * Show confirmation dialog and deletes the user account if confirmed.
   */
  deleteUser() {
    Swal.fire({
      title: '¿Estás seguro de que deseas eliminar tu cuenta?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.authService.deactivateUser(this.user?.email ?? '').subscribe(
          (response) => {
            Swal.fire('Cuenta eliminada', 'Tu cuenta ha sido eliminada correctamente', 'success');
            this.authService.logout();
          }
        );
      }
    });
  }

  /**
   * Toggles the visibility of comments.
   */
  toggleComments() {
    this.showcomments = !this.showcomments;
    if (this.showcomments) {
      this.showComments();
    }
  }


  /**
   * Retrieves and displays the comments for the user.
   */
  showComments() {
    this.commentsService.getUserComments(this.id).subscribe(
      (comments: any) => { // Change the type to Comments[]
        this.comments = comments.filter((comment: Comments) => comment.activo);
        this.comments.forEach((comments) => {
          this.getAnswersCommentsId(comments.id);
        });
      }
    );
  }

  /**
   * Retrieves the answer for the comment ID.
   * @param id - The ID of the comment.
   */
  getAnswersCommentsId(id: number): void {
    this.answerService.getAnswersCommentsId(id).subscribe(
      (answers: any) => {
        this.commentAnswers[id] = answers;
      }
    );
  }

  /**
   * Deletes a comment with the specified ID.
   * @param id - The ID of the comment to delete.
   */
  deleteComment(id: number) {
    Swal.fire({
      title: '¿Estás seguro de que deseas eliminar tu comentario?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.commentsService.deactivateComment(id).subscribe(
          (response) => {
            this.showComments();
          },
          (error) => {
            Swal.fire('Error', 'Error de conexión en el servidor', 'error');
          }
        );
      }
    });
  }


  /**
   * Changes the editComment property to true.
   * @param comment - The comment to be edited.
   */
  public editComments(comment: any) {
    this.editComment = true;
  }



  /**
   * Show an input dialog for change the comment.
   * @param comment - The comment to be saved.
   */
  public async saveComment(comment: Comments) {
    const { value: text } = await Swal.fire({
      input: "textarea",
      inputValue: comment.contenido,
      inputLabel: "Comentario",
      inputPlaceholder: "Escribe aqui tu comentario...",
      inputAttributes: {
        "aria-label": "Deja tu comentario aquí!"
      },
      showCancelButton: true
    });

    if (text) {
      comment.contenido = text;
      Swal.fire(text);
      this.commentsService.modifyComment(comment.id, comment.contenido).subscribe(
        (response) => {

        },
        (error) => {
          Swal.fire('Error', 'Error de conexión en el servidor intentalo de nuevo más tarde', 'error');
        }
      );
    }

    this.editComment = false;
  }

  /**
   * Saves the changes made by the user.
   * If the user's name is empty, displays an error message.
   * If a new password is provided and its the same to the confirmPassword, updates the user's password.
   */
  saveChanges() {
    if (!this.user.nombre) {
      Swal.fire('Error', 'El nombre no puede estar vacío', 'error');
      return;
    }
    this.userChanges = {
      name: new UpperCasePipe().transform(this.user?.nombre),
      apellidos: new UpperCasePipe().transform(this.user?.apellidos),
      password: this.user?.password
    };

    if (this.newPassword || this.confirmPassword) {
      if (this.newPassword !== this.confirmPassword) {
        Swal.fire('Error', 'Las contraseñas no coinciden', 'error');
        return;
      }
      this.userChanges.password = bcrypt.hashSync(this.newPassword, 10);
    }

    this.userService.updateUser(this.user?.id.toString() ?? '', this.userChanges).subscribe(
      (response) => {
        Swal.fire('Cambios guardados', 'Los cambios se han guardado correctamente', 'success');
        this.showEditUser = false;
        this.newPassword = '';
        this.confirmPassword = '';
      },
      (error) => {
        Swal.fire('Error', 'Error de conexión en el servidor intentelo de nuevo más tarde', 'error');
      }
    );
  }

  /**
   * Toggles the visibility of the edit user form.
   */
  toggleEditForm() {
    this.showEditUser = !this.showEditUser;
  }

  /**
   * Retrieves the ratings for the user.
   */
  getRatings() {
    this.ratingService.getRatingUser(this.id).subscribe(
      (evaluations: any) => {
        this.evaluations = evaluations;
      }
    );
  }

  /**
   * Toggles the visibility of evaluations and call the getRatting function.
   */
  showEvaluations() {
    this.showevaluations = !this.showevaluations;
    if (this.showevaluations) {
      this.getRatings();
    }
  }

  /**
   * Deletes an evaluation by its ID.
   * @param id - The ID of the evaluation to delete.
   */
  deleteEvaluation(id: number) {
    Swal.fire({
      title: '¿Estás seguro de que deseas eliminar tu valoración?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.ratingService.deleteRating(id).subscribe(
          (response) => {
            this.getRatings();
          }
        );
      }
    });

  }
}
