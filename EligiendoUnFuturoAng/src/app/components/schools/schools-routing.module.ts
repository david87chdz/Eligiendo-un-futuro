import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SchoolListComponent } from './school-list/school-list.component';
import { SchoolDetailComponent } from './school-detail/school-detail.component';
import { RatingModalComponent } from '../rating-modal/rating-modal.component';
const routes: Routes = [
  {
    path: '', component: SchoolListComponent
  },
  {
    path: ':id', component: SchoolDetailComponent
  }
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SchoolsRoutingModule { }
