import { IBook } from 'app/shared/model/book.model';

export interface ICover {
  id?: number;
  name?: string;
  ownerId?: number;
  ownerLogin?: string;
  pictureContentType?: string;
  picture?: any;
  previewContentType?: string;
  preview?: any;
  bookToCovers?: IBook[];
}

export class Cover implements ICover {
  constructor(
    public id?: number,
    public name?: string,
    public ownerId?: number,
    public ownerLogin?: string,
    public pictureContentType?: string,
    public picture?: any,
    public previewContentType?: string,
    public preview?: any,
    public bookToCovers?: IBook[]
  ) {}
}
