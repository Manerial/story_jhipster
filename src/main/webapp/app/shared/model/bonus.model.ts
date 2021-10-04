export interface IBonus {
  id?: number;
  name?: string;
  ownerId?: number;
  ownerLogin?: string;
  dataContentType?: string;
  data?: any;
  bookId?: number;
  description?: string;
}

export class Bonus implements IBonus {
  constructor(
    public id?: number,
    public name?: string,
    public ownerId?: number,
    public ownerLogin?: string,
    public dataContentType?: string,
    public data?: any,
    public bookId?: any,
    public description?: string
  ) {}
}
