import { IChapter } from 'app/entities/chapter/chapter.model';
import { IImage } from 'app/entities/image/image.model';
import { IBook } from 'app/entities/book/book.model';

export interface IPart {
  id: number;
  name?: string | null;
  description?: string | null;
  number?: number | null;
  chapters?: Pick<IChapter, 'id'>[] | null;
  images?: Pick<IImage, 'id'>[] | null;
  book?: Pick<IBook, 'id'> | null;
}

export type NewPart = Omit<IPart, 'id'> & { id: null };
