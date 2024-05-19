import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminAreaComponent } from './admin-area/admin-area.component';
import { UserAreaComponent } from './user-area/user-area.component';
import { CollegeAreaComponent } from './college-area/college-area.component';
import { adminGuard } from 'src/app/guards/admin.guard';
import { userGuard } from 'src/app/guards/user.guard';
import { schoolGuard } from 'src/app/guards/school.guard';


const routes: Routes = [
  { path: "Admin", component: AdminAreaComponent, canActivate: [adminGuard] },
  { path: "User", component: UserAreaComponent, canActivate: [userGuard] },
  { path: "School", component: CollegeAreaComponent, canActivate: [schoolGuard] }
]


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PrivateAreaRoutingModule { }
