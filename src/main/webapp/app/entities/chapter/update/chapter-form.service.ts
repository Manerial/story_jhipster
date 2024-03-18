import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IChapter, NewChapter } from '../chapter.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IChapter for edit and NewChapterFormGroupInput for create.
 */
type ChapterFormGroupInput = IChapter | PartialWithRequiredKeyOf<NewChapter>;

type ChapterFormDefaults = Pick<NewChapter, 'id' | 'images'>;

type ChapterFormGroupContent = {
  id: FormControl<IChapter['id'] | NewChapter['id']>;
  name: FormControl<IChapter['name']>;
  description: FormControl<IChapter['description']>;
  number: FormControl<IChapter['number']>;
  images: FormControl<IChapter['images']>;
  part: FormControl<IChapter['part']>;
};

export type ChapterFormGroup = FormGroup<ChapterFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ChapterFormService {
  createChapterFormGroup(chapter: ChapterFormGroupInput = { id: null }): ChapterFormGroup {
    const chapterRawValue = {
      ...this.getFormDefaults(),
      ...chapter,
    };
    return new FormGroup<ChapterFormGroupContent>({
      id: new FormControl(
        { value: chapterRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(chapterRawValue.name),
      description: new FormControl(chapterRawValue.description),
      number: new FormControl(chapterRawValue.number),
      images: new FormControl(chapterRawValue.images ?? []),
      part: new FormControl(chapterRawValue.part),
    });
  }

  getChapter(form: ChapterFormGroup): IChapter | NewChapter {
    return form.getRawValue() as IChapter | NewChapter;
  }

  resetForm(form: ChapterFormGroup, chapter: ChapterFormGroupInput): void {
    const chapterRawValue = { ...this.getFormDefaults(), ...chapter };
    form.reset(
      {
        ...chapterRawValue,
        id: { value: chapterRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ChapterFormDefaults {
    return {
      id: null,
      images: [],
    };
  }
}
