export interface IBookStatus {
  id: number;
  finished: boolean;
  favorit: boolean;
  bookId: number;
  curentChapterId: number;
  userId: number;
}

export class BookStatus implements IBookStatus {
  public id: number;
  public finished: boolean;
  public favorit: boolean;
  public bookId: number;
  public curentChapterId: number;
  public userId: number;

  constructor();
  constructor(id?: number, finished?: boolean, favorit?: boolean, bookId?: number, curentChapterId?: number, userId?: number);

  constructor(id?: number, finished?: boolean, favorit?: boolean, bookId?: number, curentChapterId?: number, userId?: number) {
    this.id = id || 0;
    this.finished = finished || false;
    this.favorit = favorit || false;
    this.bookId = bookId || 0;
    this.curentChapterId = curentChapterId || 0;
    this.userId = userId || 0;
  }
}
