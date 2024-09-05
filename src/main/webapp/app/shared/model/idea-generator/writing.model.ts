export interface IWriting {
  theme: string;
  style: string;
}

export class Writing implements IWriting {
  public theme: string;
  public style: string;

  constructor();

  constructor(theme?: string, style?: string) {
    this.theme = theme || '';
    this.style = style || '';
  }
}
