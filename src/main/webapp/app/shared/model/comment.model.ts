export interface IComment {
  id?: number;
  text?: string;
  bookId?: number;
  userId?: number;
  userLogin?: string;
  createdDate?: Date;
}

export class Comment implements IComment {
  constructor(
    public id?: number,
    public text?: string,
    public bookId?: number,
    public userId?: number,
    public userLogin?: string,
    public createdDate?: Date
  ) {}
}
