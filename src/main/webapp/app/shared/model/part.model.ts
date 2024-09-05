import { IChapter } from 'app/shared/model/chapter.model';

export interface IPart {
  id: number;
  name: string;
  description: string;
  number: number;
  chapters: IChapter[];
  bookId: number;
}

export class Part implements IPart {
  public id: number;
  public name: string;
  public description: string;
  public number: number;
  public chapters: IChapter[];
  public bookId: number;

  constructor();
  constructor(id?: number, name?: string, description?: string, number?: number, chapters?: IChapter[], bookId?: number);

  constructor(id?: number, name?: string, description?: string, number?: number, chapters?: IChapter[], bookId?: number) {
    this.id = id || 0;
    this.name = name || '';
    this.description = description || '';
    this.number = number || 0;
    this.chapters = chapters || [];
    this.bookId = bookId || 0;
  }
}
