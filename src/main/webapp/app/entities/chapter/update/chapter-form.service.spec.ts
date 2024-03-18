import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../chapter.test-samples';

import { ChapterFormService } from './chapter-form.service';

describe('Chapter Form Service', () => {
  let service: ChapterFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChapterFormService);
  });

  describe('Service methods', () => {
    describe('createChapterFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createChapterFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            number: expect.any(Object),
            images: expect.any(Object),
            part: expect.any(Object),
          }),
        );
      });

      it('passing IChapter should create a new form with FormGroup', () => {
        const formGroup = service.createChapterFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            number: expect.any(Object),
            images: expect.any(Object),
            part: expect.any(Object),
          }),
        );
      });
    });

    describe('getChapter', () => {
      it('should return NewChapter for default Chapter initial value', () => {
        const formGroup = service.createChapterFormGroup(sampleWithNewData);

        const chapter = service.getChapter(formGroup) as any;

        expect(chapter).toMatchObject(sampleWithNewData);
      });

      it('should return NewChapter for empty Chapter initial value', () => {
        const formGroup = service.createChapterFormGroup();

        const chapter = service.getChapter(formGroup) as any;

        expect(chapter).toMatchObject({});
      });

      it('should return IChapter', () => {
        const formGroup = service.createChapterFormGroup(sampleWithRequiredData);

        const chapter = service.getChapter(formGroup) as any;

        expect(chapter).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IChapter should not enable id FormControl', () => {
        const formGroup = service.createChapterFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewChapter should disable id FormControl', () => {
        const formGroup = service.createChapterFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
