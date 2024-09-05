export class InputPattern {
  forceNumber(event: any): void {
    if (!/[0-9]/.test(event.key)) {
      event.preventDefault();
    }
  }
}
