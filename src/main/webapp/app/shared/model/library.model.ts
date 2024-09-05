export interface ILibrary {
  id?: number;
  finished?: boolean;
  favorit?: boolean;
  bookId?: number;
  curentChapterId?: number;
}

export class Library implements ILibrary {
  constructor(
    public id?: number,
    public finished?: boolean,
    public favorit?: boolean,
    public bookId?: number,
    public curentChapterId?: number
  ) {
    this.finished = this.finished || false;
    this.favorit = this.favorit || false;
  }
}
