import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { SchoolsService } from 'src/app/services/schools.service';
import { School } from 'src/app/interfaces/School';
import { AuthService } from 'src/app/services/auth.service';
import { ImagesService } from 'src/app/services/images.service';
import { Images } from 'src/app/interfaces/Images';
import { jwtDecode } from 'jwt-decode';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-school-list',
  templateUrl: './school-list.component.html',
  styleUrls: ['./school-list.component.scss']
})
export class SchoolListComponent implements OnInit {
  private token = sessionStorage.getItem('Token');
  public userName: string = '';
  public schools: School[] = [];
  public searchText: string = '';
  public filterComedor: boolean = false;
  public filterConcierto: boolean = false;
  public filterNaturaleza: string = '';
  public filterProvincia: string = '';
  public filterNivel: string = '';
  public provincias: string[] = ['AVILA', 'BURGOS', 'LEÓN', 'PALENCIA', 'SALAMANCA', 'SEGOVIA', 'SORIA', 'VALLADOLID', 'ZAMORA'];
  public niveles: string[] = ['PRIMARIA', 'SECUNDARIA', 'INFANTIL', 'FORMACION PROFESIONAL', 'ADULTAS', 'SECUNDARIA NO OBLIGATORIA', 'EDUCACION ESPECIAL', 'CONSERVATORIO', 'ESCUELA DE ARTE'];

  //Not use in this version
  public photos: Images[] = [];

  public loader = false;

  constructor(private schoolService: SchoolsService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef,
    private imagenService: ImagesService
  ) { }



  ngOnInit(): void {
    if (this.token) {
      const decodedToken: any = jwtDecode(this.token);
      this.userName = decodedToken.nombre;
    }
    this.getSchools();
    this.getImages();
  }

  /**
   * Retrieves the list of schools from the school service and filters out inactive schools.
   */
  getSchools(): void {
    this.schoolService.getSchools().subscribe(
      (schools: School[]) => {
        this.schools = schools.filter(school => school.activo);
      },
      error => {
        Swal.fire('Error', 'Error de conexión con el servidor. Itentelo de nuevo más tarde.', 'error');
      }
    );
  }

  /**
   * Gets the filtered list of schools based on the search criteria.
   * @returns An array of School objects that match the search criteria.
   */
  get filteredSchools(): School[] {
    let filtered = this.schools;
    if (this.searchText) {
      filtered = filtered.filter(school =>
        school.nombre.toLowerCase().includes(this.searchText.toLowerCase())
      );
    }
    if (this.filterComedor) {
      filtered = filtered.filter(school => school.comedor);
    }
    if (this.filterConcierto) {
      filtered = filtered.filter(school => school.concierto);
    }
    if (this.filterProvincia) {
      filtered = filtered.filter(school => school.provincia === this.filterProvincia);
    }
    if (this.filterNivel) {
      if (this.filterNivel === 'SECUNDARIA NO OBLIGATORIA') {
        filtered = filtered.filter(school => school.denominacion.includes('SECUNDARIA') && !school.denominacion.includes('SECUNDARIA OBLIGATORIA'));
      } else {
        filtered = filtered.filter(school => school.denominacion.includes(this.filterNivel));
      }
    }
    if (this.filterNaturaleza) {
      filtered = filtered.filter(school => school.naturaleza === this.filterNaturaleza);
    }
    return filtered;
  }



  /**
   * Retrieves the images from the image service and filters them based on the active status of the associated school.
   */
  getImages(): void {
    this.imagenService.getImages().subscribe(
      (images: Images[]) => {
        this.photos = images.filter((image: any) => image.activo);
        this.loader = true;
      },
      error => {
        Swal.fire('Error', 'Error de conexión con el servidor. Itentelo de nuevo más tarde.', 'error');;
      }
    );

  }


  /**
   * Retrieves the description(url in this case) of the first image associated with a given school ID.
   * @param id - The ID of the school.
   * @returns The description of the first image, or an empty string if no description is available.
   */
  getFirstImageDescription(id: number): string {
    const image = this.photos.find((image: any) => image.colegio.id === id && image.colegio.activo);
    if (image && image.descripcion) {
      return image.descripcion;
    } else {
      return '';
    }

  }



}
