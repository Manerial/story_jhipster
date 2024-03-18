import { IWordAnalysis, NewWordAnalysis } from './word-analysis.model';

export const sampleWithRequiredData: IWordAnalysis = {
  id: 30214,
};

export const sampleWithPartialData: IWordAnalysis = {
  id: 5932,
  type: 'blême pauvre',
  analysis: 'bang miaou pourvu que',
};

export const sampleWithFullData: IWordAnalysis = {
  id: 25179,
  type: 'lors de',
  name: 'gens',
  analysis: 'areu areu',
};

export const sampleWithNewData: NewWordAnalysis = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
