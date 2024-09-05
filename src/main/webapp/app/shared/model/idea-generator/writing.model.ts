export interface IWriting {
  theme?: string;
  style?: string;
}

export class Writing implements IWriting {
  constructor(public theme?: string, public style?: string) {}
}
