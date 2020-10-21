import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ResponsiveService {
  private bigWidthLimit = 850;
  private smallWidthLimit = 400;
  public isBigScreen = this.checkBigScreen();
  public isMediumScreen = this.checkMediumScreen();
  public isSmallScreen = this.checkSmallScreen();

  public onResize(): void {
    this.isBigScreen = this.checkBigScreen();
    this.isMediumScreen = this.checkMediumScreen();
    this.isSmallScreen = this.checkSmallScreen();
  }

  private checkBigScreen(): boolean {
    return window.innerWidth >= this.bigWidthLimit;
  }

  private checkMediumScreen(): boolean {
    return window.innerWidth < this.bigWidthLimit && window.innerWidth >= this.smallWidthLimit;
  }

  private checkSmallScreen(): boolean {
    return window.innerWidth < this.smallWidthLimit;
  }
}
