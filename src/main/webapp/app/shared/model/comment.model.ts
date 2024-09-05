export interface IComment {
  id: number;
  text: string;
  bookId: number;
  userLogin: string;
}

export class Comment implements IComment {
  public id: number;
  public text: string;
  public bookId: number;
  public userLogin: string;

  constructor();
  constructor(id?: number, text?: string, bookId?: number, userLogin?: string);

  constructor(id?: number, text?: string, bookId?: number, userLogin?: string) {
    this.id = id || 0;
    this.text = text || '';
    this.bookId = bookId || 0;
    this.userLogin = userLogin || '';
  }
}
