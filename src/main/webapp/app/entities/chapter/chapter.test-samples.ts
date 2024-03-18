import { IChapter, NewChapter } from './chapter.model';

export const sampleWithRequiredData: IChapter = {
  id: 29959,
};

export const sampleWithPartialData: IChapter = {
  id: 20225,
};

export const sampleWithFullData: IChapter = {
  id: 9709,
  name: 'organiser lécher',
  description: 'debout réchauffer au-dessus',
  number: 16982,
};

export const sampleWithNewData: NewChapter = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
