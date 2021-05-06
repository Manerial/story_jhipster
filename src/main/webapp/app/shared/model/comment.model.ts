export interface IComment {
  id?: number;
  text?: string;
}

export class Comment implements IComment {
  constructor(public id?: number, public text?: string) {}
}
