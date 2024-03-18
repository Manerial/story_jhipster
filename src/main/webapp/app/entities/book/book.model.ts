import { IPart } from 'app/entities/part/part.model';
import { IImage } from 'app/entities/image/image.model';
import { IBookStatus } from 'app/entities/book-status/book-status.model';

export interface IBook {
  id: number;
  name?: string | null;
  author?: string | null;
  parts?: Pick<IPart, 'id'>[] | null;
  images?: Pick<IImage, 'id'>[] | null;
  cover?: Pick<IImage, 'id'> | null;
  bookStatuses?: Pick<IBookStatus, 'id'>[] | null;
}

export type NewBook = Omit<IBook, 'id'> & { id: null };
