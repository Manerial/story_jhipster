import { IPhysical } from './physical.model';
import { ITraits } from './traits.model';

export interface IPersona {
  age?: number;
  job?: string;
  name?: string;
  physical?: IPhysical;
  role?: string;
  title?: string;
  traits?: ITraits;
}

export class Persona implements IPersona {
  constructor(
    public age?: number,
    public job?: string,
    public name?: string,
    public physical?: IPhysical,
    public role?: string,
    public title?: string,
    public traits?: ITraits
  ) {}
}
