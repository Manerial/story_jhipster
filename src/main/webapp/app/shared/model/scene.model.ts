import { Moment } from 'moment';
import { IImage } from 'app/shared/model/image.model';

export interface IScene {
  id?: number;
  name?: string;
  number?: number;
  text?: any;
  timestampStart?: Moment;
  images?: IImage[];
  chapterId?: number;
}

export class Scene implements IScene {
  constructor(
    public id?: number,
    public name?: string,
    public number?: number,
    public text?: any,
    public timestampStart?: Moment,
    public images?: IImage[],
    public chapterId?: number
  ) {}
}
