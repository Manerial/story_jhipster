import { IBook, NewBook } from './book.model';

export const sampleWithRequiredData: IBook = {
  id: 13767,
};

export const sampleWithPartialData: IBook = {
  id: 18176,
};

export const sampleWithFullData: IBook = {
  id: 25512,
  name: 'promener après que à seule fin de',
  author: 'sitôt que broum',
};

export const sampleWithNewData: NewBook = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
