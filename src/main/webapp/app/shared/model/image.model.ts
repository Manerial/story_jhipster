import { IBook } from 'app/shared/model/book.model';
import { IPart } from 'app/shared/model/part.model';
import { IChapter } from 'app/shared/model/chapter.model';
import { IScene } from 'app/shared/model/scene.model';

export interface IImage {
  id: number;
  name: string;
  pictureContentType: string;
  picture: any;
  previewContentType: string;
  preview: any;
  bookToCovers: IBook[];
  books: IBook[];
  parts: IPart[];
  chapters: IChapter[];
  scenes: IScene[];
}

export class Image implements IImage {
  public id: number;
  public name: string;
  public pictureContentType: string;
  public picture: any;
  public previewContentType: string;
  public preview: any;
  public bookToCovers: IBook[];
  public books: IBook[];
  public parts: IPart[];
  public chapters: IChapter[];
  public scenes: IScene[];

  constructor();

  constructor(
    id?: number,
    name?: string,
    pictureContentType?: string,
    picture?: any,
    previewContentType?: string,
    preview?: any,
    bookToCovers?: IBook[],
    books?: IBook[],
    parts?: IPart[],
    chapters?: IChapter[],
    scenes?: IScene[]
  ) {
    this.id = id || 0;
    this.name = name || '';
    this.pictureContentType = pictureContentType || '';
    this.picture = picture;
    this.previewContentType = previewContentType || '';
    this.preview = preview;
    this.bookToCovers = bookToCovers || [];
    this.books = books || [];
    this.parts = parts || [];
    this.chapters = chapters || [];
    this.scenes = scenes || [];
  }
}
