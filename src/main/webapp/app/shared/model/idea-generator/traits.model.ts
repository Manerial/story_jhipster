export interface ITraits {
  badTraits: string[];
  goodTraits: string[];
  handicaps: string[];
  caracteristics: string[];
}

export class Traits implements ITraits {
  public badTraits: string[];
  public goodTraits: string[];
  public handicaps: string[];
  public caracteristics: string[];

  constructor();

  constructor(badTraits?: string[], goodTraits?: string[], handicaps?: string[], caracteristics?: string[]) {
    this.badTraits = badTraits || [];
    this.goodTraits = goodTraits || [];
    this.handicaps = handicaps || [];
    this.caracteristics = caracteristics || [];
  }
}
