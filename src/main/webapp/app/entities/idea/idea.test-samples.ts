import { IIdea, NewIdea } from './idea.model';

export const sampleWithRequiredData: IIdea = {
  id: 9068,
};

export const sampleWithPartialData: IIdea = {
  id: 18000,
  type: 'tant hé en dedans de',
};

export const sampleWithFullData: IIdea = {
  id: 31843,
  type: 'coac coac rectangulaire dispenser',
  value: 'lectorat',
  complement: 'de tic-tac',
};

export const sampleWithNewData: NewIdea = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
