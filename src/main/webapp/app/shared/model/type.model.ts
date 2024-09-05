export interface IType {
  name: string;
  type: string;
}

export class Type implements IType {
  public name: string;
  public type: string;

  constructor();
  constructor(name?: string, type?: string);

  constructor(name?: string, type?: string) {
    this.name = name || '';
    this.type = type || '';
  }
}
