import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { FooterComponent } from './footer/footer.component';
import { ButtonModule as PrimeButtonModule } from 'primeng/button';
import { DialogModule as PrimeDialogModule } from 'primeng/dialog';
import { PasswordModule as PrimePasswordModule} from 'primeng/password';
import { ToolbarModule as PrimeToolbarModule } from 'primeng/toolbar';
import { CardModule as PrimeCardModule } from 'primeng/card';
import { CarouselModule as PrimeCarouselModule } from 'primeng/carousel';
import { GalleriaModule as PrimeGalleriaModule } from 'primeng/galleria';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    NavbarComponent,
    FooterComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    //PrimeNG components
    PrimeButtonModule,
    PrimeDialogModule,
    PrimePasswordModule,
    PrimeToolbarModule,
    PrimeCardModule,
    PrimeCarouselModule,
    PrimeGalleriaModule
  ],
  exports: [
    NavbarComponent,
    FooterComponent
  ]
})
export class SharedModule { }
