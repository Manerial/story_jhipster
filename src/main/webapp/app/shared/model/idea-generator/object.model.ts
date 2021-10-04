export interface IObject {
  object: string;
  adjective: string;
  suffix: string;
}

export class Object implements IObject {
  public object: string;
  public adjective: string;
  public suffix: string;

  constructor();

  constructor(object?: string, adjective?: string, suffix?: string) {
    this.object = object || '';
    this.adjective = adjective || '';
    this.suffix = suffix || '';
  }
}
