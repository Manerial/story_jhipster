import { IComment, NewComment } from './comment.model';

export const sampleWithRequiredData: IComment = {
  id: 24067,
};

export const sampleWithPartialData: IComment = {
  id: 16416,
};

export const sampleWithFullData: IComment = {
  id: 23580,
  text: 'encore subito',
};

export const sampleWithNewData: NewComment = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
