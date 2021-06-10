import { IPart } from 'app/shared/model/part.model';

export interface IBook {
  id: number;
  name: string;
  authorId: number;
  authorLogin: string;
  description: string;
  parts: IPart[];
  coverPreview?: Uint8Array;
  coverId: number;
  visibility: boolean;
}

export class Book implements IBook {
  public id: number;
  public name: string;
  public authorId: number;
  public authorLogin: string;
  public description: string;
  public parts: IPart[];
  public coverPreview?: Uint8Array;
  public coverId: number;
  public visibility: boolean;

  constructor();
  constructor(
    id?: number,
    name?: string,
    authorId?: number,
    authorLogin?: string,
    description?: string,
    parts?: IPart[],
    coverPreview?: Uint8Array,
    coverId?: number,
    visibility?: boolean
  );

  constructor(
    id?: number,
    name?: string,
    authorId?: number,
    authorLogin?: string,
    description?: string,
    parts?: IPart[],
    coverPreview?: Uint8Array,
    coverId?: number,
    visibility?: boolean
  ) {
    this.id = id || 0;
    this.name = name || '';
    this.authorId = authorId || 0;
    this.authorLogin = authorLogin || '';
    this.description = description || '';
    this.parts = parts || [];
    this.coverPreview = coverPreview;
    this.coverId = coverId || 0;
    this.visibility = visibility || true;
  }
}
