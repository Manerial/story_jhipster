import { IScene } from 'app/entities/scene/scene.model';
import { IImage } from 'app/entities/image/image.model';
import { IPart } from 'app/entities/part/part.model';
import { IBookStatus } from 'app/entities/book-status/book-status.model';

export interface IChapter {
  id: number;
  name?: string | null;
  description?: string | null;
  number?: number | null;
  scenes?: Pick<IScene, 'id'>[] | null;
  images?: Pick<IImage, 'id'>[] | null;
  part?: Pick<IPart, 'id'> | null;
  bookStatuses?: Pick<IBookStatus, 'id'>[] | null;
}

export type NewChapter = Omit<IChapter, 'id'> & { id: null };
