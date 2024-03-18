import { IImage, NewImage } from './image.model';

export const sampleWithRequiredData: IImage = {
  id: 27800,
};

export const sampleWithPartialData: IImage = {
  id: 16434,
  picture: '../fake-data/blob/hipster.png',
  pictureContentType: 'unknown',
};

export const sampleWithFullData: IImage = {
  id: 21221,
  name: 'enrichir',
  picture: '../fake-data/blob/hipster.png',
  pictureContentType: 'unknown',
  preview: '../fake-data/blob/hipster.png',
  previewContentType: 'unknown',
};

export const sampleWithNewData: NewImage = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
