import { IPart, NewPart } from './part.model';

export const sampleWithRequiredData: IPart = {
  id: 15786,
};

export const sampleWithPartialData: IPart = {
  id: 31185,
  description: "à l'égard de marquer",
};

export const sampleWithFullData: IPart = {
  id: 7126,
  name: 'bzzz vlan',
  description: 'brusque',
  number: 8893,
};

export const sampleWithNewData: NewPart = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
