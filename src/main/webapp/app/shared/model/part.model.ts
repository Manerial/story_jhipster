import { IChapter } from 'app/shared/model/chapter.model';

export interface IPart {
  id?: number;
  name?: string;
  description?: string;
  number?: number;
  chapters?: IChapter[];
  bookId?: number;
}

export class Part implements IPart {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public number?: number,
    public chapters?: IChapter[],
    public bookId?: number
  ) {}
}
