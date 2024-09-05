import { IScene } from 'app/shared/model/scene.model';
import { IImage } from 'app/shared/model/image.model';

export interface IChapter {
  id?: number;
  name?: string;
  description?: string;
  number?: number;
  scenes?: IScene[];
  images?: IImage[];
  partId?: number;
}

export class Chapter implements IChapter {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public number?: number,
    public scenes?: IScene[],
    public images?: IImage[],
    public partId?: number
  ) {}
}
