import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PrivateAreaRoutingModule } from './private-area-routing.module';
import { AdminAreaComponent } from './admin-area/admin-area.component';
import { UserAreaComponent } from './user-area/user-area.component';
import { CollegeAreaComponent } from './college-area/college-area.component';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

//PrimeNG components
import { ButtonModule as PrimeButtonModule } from 'primeng/button';
import { CheckboxModule as PrimeCheckBoxModule } from 'primeng/checkbox';
import { InputTextModule as PrimeInputTextModule } from 'primeng/inputtext';
import { TableModule as PrimeTableModule } from 'primeng/table';
import { ToastModule as PrimeToastModule } from 'primeng/toast';
import { FileUploadModule as PrimeFileUploadModule } from 'primeng/fileupload';
import { DialogModule, DialogModule as PrimeDialogMOdule } from 'primeng/dialog';

import { MessagesModule as PrimeMessagesModule } from 'primeng/messages';
import { PasswordModule as PrimePasswordModule } from 'primeng/password';
import { SharedModule } from 'src/app/shared/shared.module';
@NgModule({
  declarations: [
    AdminAreaComponent,
    UserAreaComponent,
    CollegeAreaComponent
  ],
  imports: [
    CommonModule,
    PrivateAreaRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    //PrimeNG components
    PrimeButtonModule,
    PrimeCheckBoxModule,
    PrimeInputTextModule,
    PrimeTableModule,
    PrimeToastModule,
    PrimeFileUploadModule,
    DialogModule,
    PrimeMessagesModule,
    PrimePasswordModule
  ]
})
export class PrivateAreaModule { }
