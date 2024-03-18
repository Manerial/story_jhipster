import dayjs from 'dayjs/esm';

import { IScene, NewScene } from './scene.model';

export const sampleWithRequiredData: IScene = {
  id: 17488,
};

export const sampleWithPartialData: IScene = {
  id: 15559,
  number: 11586,
  text: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IScene = {
  id: 32564,
  name: 'pourvu que après-demain atchoum',
  number: 1662,
  text: '../fake-data/blob/hipster.txt',
  timestampStart: dayjs('2020-10-04'),
};

export const sampleWithNewData: NewScene = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
