import { Component, HostListener } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { PlatformLocation } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Eligiendo Un Futuro';
  constructor(private translateService: TranslateService,
              private platformLocation: PlatformLocation){
    const userLang = navigator.language || 'es';
    const languageCode = userLang.split('-')[0];
    this.translateService.setDefaultLang(languageCode);
    this.translateService.use(languageCode);
     
  }


}
