import { Injectable } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { TranslateService } from '@ngx-translate/core';

@Injectable({ providedIn: 'root' })
export class TitleService {
  constructor(private translateService: TranslateService, private titleService: Title) {}

  replaceTitle(translation: string, params: any): void {
    this.translateService.get(translation, params).subscribe(title => {
      this.titleService.setTitle(title);
    });
  }
}
