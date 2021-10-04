export interface ICreature {
  name: string;
  skin: string;
  locations: string[];
  diets: string[];
  attributes: string[];
  skills: string[];
  height: number;
  weight: number;
  nbMoveMembers: number;
}

export class Creature implements ICreature {
  name: string;
  skin: string;
  locations: string[];
  diets: string[];
  attributes: string[];
  skills: string[];
  height: number;
  weight: number;
  nbMoveMembers: number;

  constructor();

  constructor(
    name?: string,
    skin?: string,
    locations?: string[],
    diets?: string[],
    attributes?: string[],
    skills?: string[],
    height?: number,
    weight?: number,
    nbMoveMembers?: number
  ) {
    this.name = name || '';
    this.skin = skin || '';
    this.locations = locations || [];
    this.diets = diets || [];
    this.attributes = attributes || [];
    this.skills = skills || [];
    this.height = height || 0;
    this.weight = weight || 0;
    this.nbMoveMembers = nbMoveMembers || -1;
  }
}
