import { IPart } from 'app/shared/model/part.model';
import { IImage } from 'app/shared/model/image.model';

export interface IBook {
  id?: number;
  name?: string;
  author?: string;
  parts?: IPart[];
  images?: IImage[];
  coverId?: number;
}

export class Book implements IBook {
  constructor(
    public id?: number,
    public name?: string,
    public author?: string,
    public parts?: IPart[],
    public images?: IImage[],
    public coverId?: number
  ) {}
}
