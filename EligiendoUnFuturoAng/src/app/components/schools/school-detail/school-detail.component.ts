import { Component, NgModule } from '@angular/core';
import { ActivatedRoute, ParamMap, Route, Router } from '@angular/router';
import { School } from 'src/app/interfaces/School';
import { SchoolsService } from 'src/app/services/schools.service';
import { map, catchError } from 'rxjs/operators';
import { of, Observable } from 'rxjs';
import { CommentsService } from 'src/app/services/comments.service';
import { Comments } from 'src/app/interfaces/Comment';
import { AnswersService } from 'src/app/services/answers.service';
import { Answers } from 'src/app/interfaces/Answers';
import { RatingsService } from 'src/app/services/ratings.service';
import { Evaluations } from 'src/app/interfaces/Evaluations';

import { MatDialog } from '@angular/material/dialog';
import { User } from 'src/app/interfaces/User';
import { AuthService } from 'src/app/services/auth.service';
import { ChangeDetectorRef } from '@angular/core';
import { RatingModalComponent } from '../../rating-modal/rating-modal.component';
import { ActivitiesService } from 'src/app/services/activities.service';
import { Activities } from 'src/app/interfaces/Activities';
import { ImagesService } from 'src/app/services/images.service';
import { DomSanitizer } from '@angular/platform-browser';
import { CordinatesService } from 'src/app/services/cordinates.service';
import { jwtDecode } from 'jwt-decode';
import { UsersService } from 'src/app/services/users.service';
import Swal from 'sweetalert2';

/* const TOKEN = sessionStorage.getItem('Token'); */
@Component({
  selector: 'app-school-detail',
  templateUrl: './school-detail.component.html',
  styleUrls: ['./school-detail.component.scss'],
})

export class SchoolDetailComponent {
  private id!: number;
  private token: any = sessionStorage.getItem('Token');
  private userEmail: string = '';
  private userId: number = 0;
  public userRole: string = '';
  public comments: Comments[] = [];
  public answers: Answers[] = [];
  public isSchool: boolean = false;

  activities: Activities[] = [];
  evaluations: Evaluations[] = [];
  opinion: string = '';
  public user!: any;
  modalOpen = false;
  public images: any[] = [];
  public vote: boolean = true;
  public writeComment: boolean = true;
  public miLatitud: number = 0;
  public miLongitud: number = 0;
  public latitudEscuela: number = 0;
  public longitudEscuela: number = 0;
  public loader: boolean = false;
  /*   http: any; */

  constructor(
    private activatedRoute: ActivatedRoute,
    private schoolService: SchoolsService,
    private commetsService: CommentsService,
    private answersService: AnswersService,
    private ratingsService: RatingsService,
    private authService: AuthService,
    private activitiesService: ActivitiesService,
    private cdr: ChangeDetectorRef,
    public modal: MatDialog,
    public dialog: MatDialog,
    private imagesService: ImagesService,
    private cordinatesService: CordinatesService,
    private userService: UsersService,
    private router: Router
  ) {
    this.activatedRoute.paramMap.subscribe((param: ParamMap) => {
      this.id = parseInt(param.get('id')!);
      this.getSchools();
    });

    this.getComments(this.id);
    this.getActivities(this.id);
    this.getImagesId(this.id);
    this.getCordinatesClient();

    /* setTimeout(() => {
      this.loader = true;
    }, 2100); */
  }

  public school!: School;

  ngOnInit(): void {
    if (this.token) {
      let decodedToken: any = jwtDecode(this.token);
      this.userEmail = decodedToken.email;
      this.userRole = decodedToken.rol;
      if (this.userRole == 'School') {
        this.isSchool = true;
        this.writeComment = false;
      }
      this.userId = decodedToken.id;
    }
    this.getSchools();
    this.getUser(this.userId);

    /* setTimeout(() => {
      this.loader = true;
      
    }, 1000); */
  }

  getUser(id: number) {
    this.userService.getUser(id).subscribe((user) => {
      this.user = user;
      this.cdr.detectChanges();
    });
  }

  /**
   * Retrieves the schools and filters them based on the provided ID.
   * If a school with the ID is found, it assigns the filtered school to the schools array.
   */
  public getSchools(): void {
    this.schoolService
      .getSchools()
      .pipe(
        map((schools: School[]) => {
          return schools.find((school) => school.id === this.id) || null;
        }),
        catchError((error: any) => {
          this.router.navigate(['/schools']);
          return of(null);
        })
      )
      .subscribe((filteredSchool: School | null) => {
        if (filteredSchool !== null) {
          this.school = filteredSchool;
          this.getCordinatesSchool(this.school.codigo);
          this.getEvaluations(this.id);
        } else {
          this.router.navigate(['/schools']);
         
        }
      });

  }

  /**
   * Retrieves the comments for the school with the provided ID from the server.
   * @param {number} id The ID of the school.
   */
  public getComments(id: number): void {
    this.commetsService.getCommentsSchoolId(this.id).subscribe(
      (comments: Comments[]) => {
        this.comments = comments;

        this.getAnswer();
      }

    );
  }

  /**
   * Retrieves answers from the answers service.
   * Subscribes to the answers service to get the answers and assigns them to the ansnswes array
   */
  public getAnswer(): void {
    this.answersService.getAnswers().subscribe(
      (answer: any) => {
        this.answers = answer;
      }
    );
  }

  /**
   * Retrieves the answers that have a specific comment ID.
   * @param {number} id - The ID of the comment.
   * @returns An array of answers that have the specified comment ID.
   */
  getAnswersCommentsId(id: number): any {
    const answer = this.answers.filter((answer) => answer.comentario.id == id);
    return answer;
  }

  /**
   * Retrieves evaluations for a specific school.
   * @param id - The ID of the school.
   */
  getEvaluations(id: number): void {
    this.ratingsService.getEvaluationsSchoolId(id).subscribe(
      (evaluations: Evaluations[]) => {
        this.evaluations = evaluations;
        for (let evaluation of this.evaluations) {
          if (evaluation.usuario.id == this.user?.id) {
            this.vote = false;
          }
        }
      }
    );
    this.loader = true;
  }



  /**
   * Sends the user's opinion to the Comments Service.
   */
  async sendOpinion(): Promise<void> {
    const comment: any = {
      usuarioId: this.user?.id,
      colegioId: this.id,
    };
    const { value: text } = await Swal.fire({
      input: "textarea",
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
    }
    this.commetsService.addComment(comment).subscribe(
      (response) => {
        this.getComments(this.id);
      },
      (error) => {
        Swal.fire('Error', 'Error de conexión con el servidor. Itentelo de nuevo más tarde.', 'error');
      }
    );
  }




  /**
   * Opens the rating modal dialog.
   */
  public openRatingModal() {
    let dialogRef = this.dialog.open(RatingModalComponent);
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.sendRating(result);
        this.getSchools();
      }
    });
  }

  /**
     * Sends a rating for the school.
     * @param {number} stars - The number of stars for the rating.
     */
  public sendRating(stars: number): void {
    const rating: any = {
      puntuacion: stars,
      usuarioId: this.user?.id,
      colegioId: this.id,
    };
    this.ratingsService.addRating(rating).subscribe(
      (response) => {
        this.getEvaluations(this.id);
      },
      (error) => {
        Swal.fire('Error', 'Error de conexión con el servidor. Itentelo de nuevo más tarde.', 'error');
      }
    );
  }

  /**
   * Retrieves activities by school ID.
   * @param {number} id - The ID of the school.
   */
  public getActivities(id: number): void {
    this.activitiesService.getActivitiesBySchoolId(this.id).subscribe(
      (activities: any) => {
        this.activities = activities;
      }
    );
  }

  /**
   * Retrieves the images for a given school ID.
   * @param {number} id - The ID of the school.
   */
  public getImagesId(id: number) {
    this.imagesService.getImagesBySchoolId(id).subscribe(
      (urls) => (this.images = urls),
    );
  }

  /**
   * Retrieves the coordinates of the client using the Geolocation API.
   * If the browser supports geolocation, it will retrieve the current position
   * and assign the latitude and longitude values to the corresponding properties.
   * If the browser does not support geolocation, it will log an error message.
   */
  public getCordinatesClient() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.miLatitud = position.coords.latitude;
        this.miLongitud = position.coords.longitude;
      });
    } else {
      Swal.fire('Error', 'No se puede obtener su ubicación', 'error');
    }
  }

  /**
   * Retrieves the coordinates of a school based on its code.
   * @param codigo - The code of the school.
   */
  public getCordinatesSchool(codigo: string) {
    this.cordinatesService.getSchoolByCode(codigo).subscribe(
      (coordinates) => {
        if (coordinates) {
          this.longitudEscuela = coordinates[0];
          this.latitudEscuela = coordinates[1];
        }
      }
    );
  }
}
