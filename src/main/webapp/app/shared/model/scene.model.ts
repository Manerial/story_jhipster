export interface IScene {
  id?: number;
  name?: string;
  number?: number;
  text?: any;
  timestampStart?: Date;
  chapterId?: number;
}

export class Scene implements IScene {
  constructor(
    public id?: number,
    public name?: string,
    public number?: number,
    public text?: any,
    public timestampStart?: Date,
    public chapterId?: number
  ) {}
}
