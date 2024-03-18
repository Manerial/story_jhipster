import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../word-analysis.test-samples';

import { WordAnalysisFormService } from './word-analysis-form.service';

describe('WordAnalysis Form Service', () => {
  let service: WordAnalysisFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WordAnalysisFormService);
  });

  describe('Service methods', () => {
    describe('createWordAnalysisFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createWordAnalysisFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            name: expect.any(Object),
            analysis: expect.any(Object),
          }),
        );
      });

      it('passing IWordAnalysis should create a new form with FormGroup', () => {
        const formGroup = service.createWordAnalysisFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            name: expect.any(Object),
            analysis: expect.any(Object),
          }),
        );
      });
    });

    describe('getWordAnalysis', () => {
      it('should return NewWordAnalysis for default WordAnalysis initial value', () => {
        const formGroup = service.createWordAnalysisFormGroup(sampleWithNewData);

        const wordAnalysis = service.getWordAnalysis(formGroup) as any;

        expect(wordAnalysis).toMatchObject(sampleWithNewData);
      });

      it('should return NewWordAnalysis for empty WordAnalysis initial value', () => {
        const formGroup = service.createWordAnalysisFormGroup();

        const wordAnalysis = service.getWordAnalysis(formGroup) as any;

        expect(wordAnalysis).toMatchObject({});
      });

      it('should return IWordAnalysis', () => {
        const formGroup = service.createWordAnalysisFormGroup(sampleWithRequiredData);

        const wordAnalysis = service.getWordAnalysis(formGroup) as any;

        expect(wordAnalysis).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IWordAnalysis should not enable id FormControl', () => {
        const formGroup = service.createWordAnalysisFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewWordAnalysis should disable id FormControl', () => {
        const formGroup = service.createWordAnalysisFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
