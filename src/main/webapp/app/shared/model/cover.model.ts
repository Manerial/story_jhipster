import { IBook } from 'app/shared/model/book.model';

export interface ICover {
  id: number;
  name: string;
  pictureContentType: string;
  picture: any;
  previewContentType: string;
  preview: any;
  bookToCovers: IBook[];
}

export class Cover implements ICover {
  public id: number;
  public name: string;
  public pictureContentType: string;
  public picture: any;
  public previewContentType: string;
  public preview: any;
  public bookToCovers: IBook[];

  constructor();
  constructor(
    id?: number,
    name?: string,
    pictureContentType?: string,
    picture?: any,
    previewContentType?: string,
    preview?: any,
    bookToCovers?: IBook[]
  );

  constructor(
    id?: number,
    name?: string,
    pictureContentType?: string,
    picture?: any,
    previewContentType?: string,
    preview?: any,
    bookToCovers?: IBook[]
  ) {
    this.id = id || 0;
    this.name = name || '';
    this.pictureContentType = pictureContentType || '';
    this.picture = picture;
    this.previewContentType = previewContentType || '';
    this.preview = preview;
    this.bookToCovers = bookToCovers || [];
  }
}
