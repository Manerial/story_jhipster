import { IScene } from 'app/shared/model/scene.model';

export interface IChapter {
  id: number;
  name: string;
  description: string;
  number: number;
  scenes: IScene[];
  partId: number;
}

export class Chapter implements IChapter {
  public id: number;
  public name: string;
  public description: string;
  public number: number;
  public scenes: IScene[];
  public partId: number;

  constructor();
  constructor(id?: number, name?: string, description?: string, number?: number, scenes?: IScene[], partId?: number);

  constructor(id?: number, name?: string, description?: string, number?: number, scenes?: IScene[], partId?: number) {
    this.id = id || 0;
    this.name = name || '';
    this.description = description || '';
    this.number = number || 0;
    this.scenes = scenes || [];
    this.partId = partId || 0;
  }
}
