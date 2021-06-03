export interface IBonus {
  id?: number;
  name?: string;
  extension?: string;
}

export class Bonus implements IBonus {
  constructor(public id?: number, public name?: string, public extension?: string) {}
}
