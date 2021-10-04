export interface IBookStatus {
  id?: number;
  finished?: boolean;
  favorit?: boolean;
  bookId?: number;
  curentChapterId?: number;
  userId?: number;
}

export class BookStatus implements IBookStatus {
  constructor(
    public id?: number,
    public finished?: boolean,
    public favorit?: boolean,
    public bookId?: number,
    public curentChapterId?: number,
    public userId?: number
  ) {}
}
