import { IChapter } from 'app/shared/model/chapter.model';
import { IImage } from 'app/shared/model/image.model';

export interface IPart {
  id?: number;
  name?: string;
  description?: string;
  number?: number;
  chapters?: IChapter[];
  images?: IImage[];
  bookId?: number;
}

export class Part implements IPart {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public number?: number,
    public chapters?: IChapter[],
    public images?: IImage[],
    public bookId?: number
  ) {}
}
