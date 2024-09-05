import { IPart } from 'app/shared/model/part.model';
import { IImage } from 'app/shared/model/image.model';

export interface IBook {
  id: number;
  name: string;
  author: string;
  description: string;
  parts: IPart[];
  images: IImage[];
  coverPreview?: Uint8Array;
  coverId: number;
}

export class Book implements IBook {
  public id: number;
  public name: string;
  public author: string;
  public description: string;
  public parts: IPart[];
  public images: IImage[];
  public coverPreview?: Uint8Array;
  public coverId: number;

  constructor();
  constructor(
    id?: number,
    name?: string,
    author?: string,
    description?: string,
    parts?: IPart[],
    images?: IImage[],
    coverPreview?: Uint8Array,
    coverId?: number
  );

  constructor(
    id?: number,
    name?: string,
    author?: string,
    description?: string,
    parts?: IPart[],
    images?: IImage[],
    coverPreview?: Uint8Array,
    coverId?: number
  ) {
    this.id = id || 0;
    this.name = name || '';
    this.author = author || '';
    this.description = description || '';
    this.parts = parts || [];
    this.images = images || [];
    this.coverPreview = coverPreview;
    this.coverId = coverId || 0;
  }
}
