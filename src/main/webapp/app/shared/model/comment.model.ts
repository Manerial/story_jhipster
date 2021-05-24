export interface IComment {
  id: number;
  text: string;
  bookId: number;
  userId: number;
  userLogin: string;
  createdDate: Date;
}

export class Comment implements IComment {
  public id: number;
  public text: string;
  public bookId: number;
  public userId: number;
  public userLogin: string;
  public createdDate: Date;

  constructor();
  constructor(id?: number, text?: string, bookId?: number, userId?: number, userLogin?: string, createdDate?: Date);

  constructor(id?: number, text?: string, bookId?: number, userId?: number, userLogin?: string, createdDate?: Date) {
    this.id = id || 0;
    this.text = text || '';
    this.bookId = bookId || 0;
    this.userId = userId || 0;
    this.userLogin = userLogin || '';
    this.createdDate = createdDate || new Date();
  }
}
