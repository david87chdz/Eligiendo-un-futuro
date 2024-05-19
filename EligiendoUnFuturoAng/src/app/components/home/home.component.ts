import { Component } from '@angular/core';
import { ImagesService } from 'src/app/services/images.service';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {



  public images: any[] = []

  constructor(private imagesService: ImagesService) {
    this.getImages();
  }


  getImages() {
    this.imagesService.getImages().subscribe(
      response => {
        this.images = response.filter((image: any) => image.colegio.activo);
      },
      error => {
        console.error('Error:', error);
      }
    );
  }
}
