import { IPhysical, Physical } from './physical.model';
import { ITraits, Traits } from './traits.model';

export interface IPersona {
  age: number;
  job: string;
  name: string;
  physical: IPhysical;
  role: string;
  title: string;
  traits: ITraits;
}

export class Persona implements IPersona {
  public age: number;
  public job: string;
  public name: string;
  public physical: IPhysical;
  public role: string;
  public title: string;
  public traits: ITraits;

  constructor();

  constructor(age?: number, job?: string, name?: string, physical?: IPhysical, role?: string, title?: string, traits?: ITraits) {
    this.age = age || 0;
    this.job = job || '';
    this.name = name || '';
    this.physical = physical || new Physical();
    this.role = role || '';
    this.title = title || '';
    this.traits = traits || new Traits();
  }
}
