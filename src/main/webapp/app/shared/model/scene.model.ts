import { IImage } from 'app/shared/model/image.model';

export interface IScene {
  id: number;
  name: string;
  number: number;
  text: any;
  timestampStart: Date;
  images: IImage[];
  chapterId: number;
}

export class Scene implements IScene {
  public id: number;
  public name: string;
  public number: number;
  public text: any;
  public timestampStart: Date;
  public images: IImage[];
  public chapterId: number;

  constructor();

  constructor(id?: number, name?: string, number?: number, text?: any, timestampStart?: Date, images?: IImage[], chapterId?: number) {
    this.id = id || 0;
    this.name = name || '';
    this.number = number || 0;
    this.text = text || '';
    this.timestampStart = timestampStart || new Date();
    this.images = images || [];
    this.chapterId = chapterId || 0;
  }
}
