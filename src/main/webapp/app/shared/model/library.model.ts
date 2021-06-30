export interface ILibrary {
  id: number;
  finished: boolean;
  favorit: boolean;
  bookId: number;
  curentChapterId: number;
}

export class Library implements ILibrary {
  public id: number;
  public finished: boolean;
  public favorit: boolean;
  public bookId: number;
  public curentChapterId: number;

  constructor();
  constructor(id?: number, finished?: boolean, favorit?: boolean, bookId?: number, curentChapterId?: number);

  constructor(id?: number, finished?: boolean, favorit?: boolean, bookId?: number, curentChapterId?: number) {
    this.id = id || 0;
    this.finished = finished || false;
    this.favorit = favorit || false;
    this.bookId = bookId || 0;
    this.curentChapterId = curentChapterId || 0;
  }
}
