import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBookStatus, NewBookStatus } from '../book-status.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBookStatus for edit and NewBookStatusFormGroupInput for create.
 */
type BookStatusFormGroupInput = IBookStatus | PartialWithRequiredKeyOf<NewBookStatus>;

type BookStatusFormDefaults = Pick<NewBookStatus, 'id' | 'finished' | 'favorit'>;

type BookStatusFormGroupContent = {
  id: FormControl<IBookStatus['id'] | NewBookStatus['id']>;
  finished: FormControl<IBookStatus['finished']>;
  favorit: FormControl<IBookStatus['favorit']>;
  book: FormControl<IBookStatus['book']>;
  curentChapter: FormControl<IBookStatus['curentChapter']>;
};

export type BookStatusFormGroup = FormGroup<BookStatusFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BookStatusFormService {
  createBookStatusFormGroup(bookStatus: BookStatusFormGroupInput = { id: null }): BookStatusFormGroup {
    const bookStatusRawValue = {
      ...this.getFormDefaults(),
      ...bookStatus,
    };
    return new FormGroup<BookStatusFormGroupContent>({
      id: new FormControl(
        { value: bookStatusRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      finished: new FormControl(bookStatusRawValue.finished),
      favorit: new FormControl(bookStatusRawValue.favorit),
      book: new FormControl(bookStatusRawValue.book),
      curentChapter: new FormControl(bookStatusRawValue.curentChapter),
    });
  }

  getBookStatus(form: BookStatusFormGroup): IBookStatus | NewBookStatus {
    return form.getRawValue() as IBookStatus | NewBookStatus;
  }

  resetForm(form: BookStatusFormGroup, bookStatus: BookStatusFormGroupInput): void {
    const bookStatusRawValue = { ...this.getFormDefaults(), ...bookStatus };
    form.reset(
      {
        ...bookStatusRawValue,
        id: { value: bookStatusRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): BookStatusFormDefaults {
    return {
      id: null,
      finished: false,
      favorit: false,
    };
  }
}
