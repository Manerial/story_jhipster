import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../book-status.test-samples';

import { BookStatusFormService } from './book-status-form.service';

describe('BookStatus Form Service', () => {
  let service: BookStatusFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookStatusFormService);
  });

  describe('Service methods', () => {
    describe('createBookStatusFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBookStatusFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            finished: expect.any(Object),
            favorit: expect.any(Object),
            book: expect.any(Object),
            curentChapter: expect.any(Object),
          }),
        );
      });

      it('passing IBookStatus should create a new form with FormGroup', () => {
        const formGroup = service.createBookStatusFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            finished: expect.any(Object),
            favorit: expect.any(Object),
            book: expect.any(Object),
            curentChapter: expect.any(Object),
          }),
        );
      });
    });

    describe('getBookStatus', () => {
      it('should return NewBookStatus for default BookStatus initial value', () => {
        const formGroup = service.createBookStatusFormGroup(sampleWithNewData);

        const bookStatus = service.getBookStatus(formGroup) as any;

        expect(bookStatus).toMatchObject(sampleWithNewData);
      });

      it('should return NewBookStatus for empty BookStatus initial value', () => {
        const formGroup = service.createBookStatusFormGroup();

        const bookStatus = service.getBookStatus(formGroup) as any;

        expect(bookStatus).toMatchObject({});
      });

      it('should return IBookStatus', () => {
        const formGroup = service.createBookStatusFormGroup(sampleWithRequiredData);

        const bookStatus = service.getBookStatus(formGroup) as any;

        expect(bookStatus).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBookStatus should not enable id FormControl', () => {
        const formGroup = service.createBookStatusFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBookStatus should disable id FormControl', () => {
        const formGroup = service.createBookStatusFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
