export interface IHonoraryTitle {
  title: string;
}

export class HonoraryTitle implements IHonoraryTitle {
  public title: string;

  constructor();

  constructor(title?: string) {
    this.title = title || '';
  }
}
