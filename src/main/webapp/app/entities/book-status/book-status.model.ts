import { IBook } from 'app/entities/book/book.model';
import { IChapter } from 'app/entities/chapter/chapter.model';

export interface IBookStatus {
  id: number;
  finished?: boolean | null;
  favorit?: boolean | null;
  book?: Pick<IBook, 'id'> | null;
  curentChapter?: Pick<IChapter, 'id'> | null;
}

export type NewBookStatus = Omit<IBookStatus, 'id'> & { id: null };
