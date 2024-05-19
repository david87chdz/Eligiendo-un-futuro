import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SchoolsRoutingModule } from './schools-routing.module';
import { SchoolListComponent } from './school-list/school-list.component';
import { SchoolDetailComponent } from './school-detail/school-detail.component';
import { CardModule as PrimeCardModule } from 'primeng/card';
import { ButtonModule as PrimeButtonModule } from 'primeng/button';
import { CarouselModule as PrimeCarouselModule} from 'primeng/carousel';
import { MapModule } from '../maps/map/map.module';
import { DescriptionPipe } from 'src/app/pipes/description.pipe';
import { DropdownModule as PrimeDropdownModule } from 'primeng/dropdown';
import { CheckboxModule as PrimeCheckboxModule } from 'primeng/checkbox';
import { InputTextModule as PrimeInputTextModule} from 'primeng/inputtext';
import { SafeUrlPipe } from 'src/app/pipes/safe-url.pipe';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [
    SchoolListComponent,
    SchoolDetailComponent,
    DescriptionPipe,
    SafeUrlPipe,
   
  ],
  imports: [
    CommonModule,
    SchoolsRoutingModule,
    MapModule,
    FormsModule,
    SharedModule,

    //PrimeNG components
    PrimeCardModule,
    PrimeButtonModule,
    PrimeCarouselModule,
    PrimeDropdownModule,
    PrimeCheckboxModule,
    PrimeInputTextModule
  ]
})
export class SchoolsModule { }
