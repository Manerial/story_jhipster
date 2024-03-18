import { IBonus, NewBonus } from './bonus.model';

export const sampleWithRequiredData: IBonus = {
  id: 30362,
};

export const sampleWithPartialData: IBonus = {
  id: 4707,
};

export const sampleWithFullData: IBonus = {
  id: 8418,
  name: 'doucement confirmer triangulaire',
  extension: 'bien que snob diététiste',
};

export const sampleWithNewData: NewBonus = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
