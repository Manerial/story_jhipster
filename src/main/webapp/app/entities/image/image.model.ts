import { IBook } from 'app/entities/book/book.model';
import { IPart } from 'app/entities/part/part.model';
import { IChapter } from 'app/entities/chapter/chapter.model';
import { IScene } from 'app/entities/scene/scene.model';

export interface IImage {
  id: number;
  name?: string | null;
  picture?: string | null;
  pictureContentType?: string | null;
  preview?: string | null;
  previewContentType?: string | null;
  bookToCovers?: Pick<IBook, 'id'>[] | null;
  books?: Pick<IBook, 'id'>[] | null;
  parts?: Pick<IPart, 'id'>[] | null;
  chapters?: Pick<IChapter, 'id'>[] | null;
  scenes?: Pick<IScene, 'id'>[] | null;
}

export type NewImage = Omit<IImage, 'id'> & { id: null };
