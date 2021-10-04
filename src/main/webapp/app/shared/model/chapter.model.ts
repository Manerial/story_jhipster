import { IScene } from 'app/shared/model/scene.model';

export interface IChapter {
  id?: number;
  name?: string;
  description?: string;
  number?: number;
  scenes?: IScene[];
  partId?: number;
}

export class Chapter implements IChapter {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public number?: number,
    public scenes?: IScene[],
    public partId?: number
  ) {}
}
