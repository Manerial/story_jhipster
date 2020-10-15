export interface IWordAnalysis {
  id?: number;
  type?: string;
  name?: string;
  analysis?: string;
}

export class WordAnalysis implements IWordAnalysis {
  constructor(public id?: number, public type?: string, public name?: string, public analysis?: string) {}
}
