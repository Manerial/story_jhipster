export interface IBonus {
  id: number;
  name: string;
  extension: string;
  data: any;
  bookId: number;
}

export class Bonus implements IBonus {
  public id: number;
  public name: string;
  public extension: string;
  public data: any;
  public bookId: number;

  constructor();
  constructor(id?: number, name?: string, extension?: string, data?: any, bookId?: any);

  constructor(id?: number, name?: string, extension?: string, data?: any, bookId?: any) {
    this.id = id || 0;
    this.name = name || '';
    this.extension = extension || '';
    this.data = data || null;
    this.bookId = bookId || 0;
  }
}
