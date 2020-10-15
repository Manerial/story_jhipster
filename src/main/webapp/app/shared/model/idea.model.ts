export interface IIdea {
  id?: number;
  type?: string;
  value?: string;
  complement?: string;
}

export class Idea implements IIdea {
  constructor(public id?: number, public type?: string, public value?: string, public complement?: string) {}
}
