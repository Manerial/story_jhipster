import { Directive, EventEmitter, HostListener, Output } from '@angular/core';

@Directive({
  selector: '[jhiSecretCode]',
})
export class SecretCodeDirective {
  @Output()
  private jhiSecretCode: EventEmitter<void>;
  private sequence: string[];
  private secretCode: string[];

  constructor() {
    this.jhiSecretCode = new EventEmitter<void>();
    this.sequence = [];
    this.secretCode = ['s', 'i', 'l', 'e', 'n', 'c', 'e', ',', ' ', 'm', 'o', 'n', ' ', 'f', 'r', 'è', 'r', 'e'];
  }

  @HostListener('window:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    if (event.key) {
      this.sequence.push(event.key.toString());

      if (this.sequence.length > this.secretCode.length) {
        this.sequence.shift();
      }

      if (this.isSecretCode()) {
        this.jhiSecretCode.emit();
      }
    }
  }

  isSecretCode(): boolean {
    return this.secretCode.every((code: string, index: number) => code === this.sequence[index]);
  }
}
