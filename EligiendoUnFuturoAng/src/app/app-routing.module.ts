import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { AuthGuard } from './guards/auth.guard';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
const routes: Routes = [
  {path: "home", component: HomeComponent},
  {path: "", redirectTo: "/home", pathMatch: "full"},
  {path: "login", component: LoginComponent},
  {path: "signup", component: SignUpComponent},
  { path: 'reset-password/:token', component: ResetPasswordComponent },
  {
    path: "private-area",
    loadChildren: () => import('./components/private-area/private-area.module').then(m => m.PrivateAreaModule),
    canLoad: [AuthGuard]
  },
  {
    path: "schools",
    loadChildren: () => import('./components/schools/schools.module').then(m => m.SchoolsModule),
    canLoad: [AuthGuard]
  },
  {path: "**", redirectTo: "/home"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
