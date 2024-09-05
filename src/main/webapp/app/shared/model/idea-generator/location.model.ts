export interface ILocation {
  place: string;
  landscape: string;
  material: string;
}

export class Location implements ILocation {
  public place: string;
  public landscape: string;
  public material: string;

  constructor();

  constructor(place?: string, landscape?: string, material?: string) {
    this.place = place || '';
    this.landscape = landscape || '';
    this.material = material || '';
  }
}
