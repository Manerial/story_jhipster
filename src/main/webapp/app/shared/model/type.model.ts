export interface IType {
  name?: string;
  type?: string;
}

export class Type implements IType {
  constructor(public name?: string, public type?: string) {}
}
