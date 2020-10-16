export interface ITraits {
  bad_traits?: string[];
  good_traits?: string[];
  handicaps?: string[];
  caracteristics?: string[];
}

export class Traits implements ITraits {
  constructor(public bad_traits?: string[], public good_traits?: string[], public handicaps?: string[], public caracteristics?: string[]) {}
}
