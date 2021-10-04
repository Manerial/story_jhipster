import { IPart } from 'app/shared/model/part.model';

export interface IBook {
  id?: number;
  name?: string;
  authorId?: number;
  authorLogin?: string;
  description?: string;
  parts?: IPart[];
  coverPreview?: Uint8Array;
  coverId?: number;
  visibility?: boolean;
}

export class Book implements IBook {
  constructor(
    public id?: number,
    public name?: string,
    public authorId?: number,
    public authorLogin?: string,
    public description?: string,
    public parts?: IPart[],
    public coverPreview?: Uint8Array,
    public coverId?: number,
    public visibility?: boolean
  ) {
    this.visibility = visibility || true;
  }
}
