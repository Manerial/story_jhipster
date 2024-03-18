import { IBookStatus, NewBookStatus } from './book-status.model';

export const sampleWithRequiredData: IBookStatus = {
  id: 6899,
};

export const sampleWithPartialData: IBookStatus = {
  id: 619,
};

export const sampleWithFullData: IBookStatus = {
  id: 5901,
  finished: true,
  favorit: true,
};

export const sampleWithNewData: NewBookStatus = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
