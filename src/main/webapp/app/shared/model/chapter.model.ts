import { IScene } from 'app/shared/model/scene.model';
import { IImage } from 'app/shared/model/image.model';

export interface IChapter {
  id: number;
  name: string;
  description: string;
  number: number;
  scenes: IScene[];
  images: IImage[];
  partId: number;
}

export class Chapter implements IChapter {
  public id: number;
  public name: string;
  public description: string;
  public number: number;
  public scenes: IScene[];
  public images: IImage[];
  public partId: number;

  constructor();

  constructor(id?: number, name?: string, description?: string, number?: number, scenes?: IScene[], images?: IImage[], partId?: number) {
    this.id = id || 0;
    this.name = name || '';
    this.description = description || '';
    this.number = number || 0;
    this.scenes = scenes || [];
    this.images = images || [];
    this.partId = partId || 0;
  }
}
