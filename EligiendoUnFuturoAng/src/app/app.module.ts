import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';





import { BrowserAnimationsModule } from '@angular/platform-browser/animations';



import { ReactiveFormsModule } from '@angular/forms';
import { FormControl, FormGroup, FormsModule } from '@angular/forms';

//Translate modules and components
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { HttpClientModule, HttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';
export function HttpLoaderFactory(http: HttpClient){
  return new TranslateHttpLoader(http, '../assets/i18n/', '.json');
}

import { JwtModule } from '@auth0/angular-jwt';

import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { RatingModalComponent } from './components/rating-modal/rating-modal.component';

// Module to shared components navbar and footer
import { SharedModule } from './shared/shared.module';

// Modules tho lazy load components
import { SchoolsModule } from './components/schools/schools.module';
import { PrivateAreaModule } from './components/private-area/private-area.module';

//Interceptor for the requests to the server to add the token
import { JwtInterceptor } from './interceptors/jwt.interceptor';


//Modules for the Angular Material components
import { MatDialogModule } from '@angular/material/dialog';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSelectModule } from '@angular/material/select';


//PrimeNG modules and Services 
import { DialogService } from 'primeng/dynamicdialog';
import { ButtonModule as PrimeButtonModule } from 'primeng/button';
import { DialogModule as PrimeDialogModule } from 'primeng/dialog';
import { PasswordModule as PrimePasswordModule} from 'primeng/password';
import { ToolbarModule as PrimeToolbarModule } from 'primeng/toolbar';
import { CardModule as PrimeCardModule } from 'primeng/card';
import { CarouselModule as PrimeCarouselModule } from 'primeng/carousel';
import { GalleriaModule as PrimeGalleriaModule } from 'primeng/galleria';
import { DatePipe } from './pipes/date.pipe';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    SignUpComponent,
    RatingModalComponent,
    ResetPasswordComponent,
    DatePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      },
    }),
    JwtModule.forRoot({
      config: {
        tokenGetter: () => {
          return localStorage.getItem('token'); 
        },
        allowedDomains: ['localhost'], // Dominios permitidos para las peticiones
        disallowedRoutes: ['http://example.com/api/auth'] // Rutas excluidas de la verificación del token
      }
    }),

    BrowserAnimationsModule,
    ReactiveFormsModule,
    SchoolsModule,
    PrivateAreaModule,
    FormsModule,
   
    //Material components
    MatSlideToggleModule,
    MatDialogModule,
    MatToolbarModule,
    MatIconModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,

    //PrimeNG components
    PrimeButtonModule,
    PrimeDialogModule,
    PrimePasswordModule,
    PrimeToolbarModule,
    PrimeCardModule,
    PrimeCarouselModule,
    PrimeGalleriaModule,

     //Shared Module
     SharedModule
  ],
  providers: [  { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }, DialogService ],// Registra tu interceptor],
  bootstrap: [AppComponent]
})
export class AppModule { }
