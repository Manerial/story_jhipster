import { Directive, EventEmitter, HostListener, Output } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Directive({
  selector: '[jhiSecretCode]',
})
export class SecretCodeDirective {
  @Output()
  private jhiSecretCode: EventEmitter<string>;
  private sequence: string[][] = [];
  private secretCode: string[][] = [];

  constructor(private translateService: TranslateService) {
    this.jhiSecretCode = new EventEmitter<string>();

    this.secretCode.push(['s', 'i', 'l', 'e', 'n', 'c', 'e', ',', ' ', 'm', 'o', 'n', ' ', 'f', 'r', 'è', 'r', 'e']);
    this.secretCode.push(['ArrowUp', 'ArrowUp', 'ArrowDown', 'ArrowDown', 'ArrowLeft', 'ArrowRight', 'ArrowLeft', 'ArrowRight', 'b', 'a']);

    this.secretCode.forEach(() => {
      this.sequence.push([]);
    });
  }

  @HostListener('window:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.key) {
      this.sequence.forEach((element, index) => {
        element.push(event.key.toString());
        // eslint-disable-next-line no-console
        // console.log(event.key.toString());

        if (element.length > this.secretCode[index].length) {
          element.shift();
        }

        if (this.isSecretCode(index)) {
          this.jhiSecretCode.emit(this.translateService.instant('global.secret.' + (index + 1)));
        }
      });
    }
  }

  isSecretCode(codeIndex: number): boolean {
    return this.secretCode[codeIndex].every((code: string, index: number) => code === this.sequence[codeIndex][index]);
  }
}
