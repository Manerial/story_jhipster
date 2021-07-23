export interface IWritingOption {
  theme: string;
  style: string;
}

export class WritingOption implements IWritingOption {
  public theme: string;
  public style: string;

  constructor();

  constructor(theme?: string, style?: string) {
    this.theme = theme || '';
    this.style = style || '';
  }
}
