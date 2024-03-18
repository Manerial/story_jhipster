import dayjs from 'dayjs/esm';
import { IImage } from 'app/entities/image/image.model';
import { IChapter } from 'app/entities/chapter/chapter.model';

export interface IScene {
  id: number;
  name?: string | null;
  number?: number | null;
  text?: string | null;
  timestampStart?: dayjs.Dayjs | null;
  images?: Pick<IImage, 'id'>[] | null;
  chapter?: Pick<IChapter, 'id'> | null;
}

export type NewScene = Omit<IScene, 'id'> & { id: null };
