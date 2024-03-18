import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPart, NewPart } from '../part.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPart for edit and NewPartFormGroupInput for create.
 */
type PartFormGroupInput = IPart | PartialWithRequiredKeyOf<NewPart>;

type PartFormDefaults = Pick<NewPart, 'id' | 'images'>;

type PartFormGroupContent = {
  id: FormControl<IPart['id'] | NewPart['id']>;
  name: FormControl<IPart['name']>;
  description: FormControl<IPart['description']>;
  number: FormControl<IPart['number']>;
  images: FormControl<IPart['images']>;
  book: FormControl<IPart['book']>;
};

export type PartFormGroup = FormGroup<PartFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PartFormService {
  createPartFormGroup(part: PartFormGroupInput = { id: null }): PartFormGroup {
    const partRawValue = {
      ...this.getFormDefaults(),
      ...part,
    };
    return new FormGroup<PartFormGroupContent>({
      id: new FormControl(
        { value: partRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(partRawValue.name),
      description: new FormControl(partRawValue.description),
      number: new FormControl(partRawValue.number),
      images: new FormControl(partRawValue.images ?? []),
      book: new FormControl(partRawValue.book),
    });
  }

  getPart(form: PartFormGroup): IPart | NewPart {
    return form.getRawValue() as IPart | NewPart;
  }

  resetForm(form: PartFormGroup, part: PartFormGroupInput): void {
    const partRawValue = { ...this.getFormDefaults(), ...part };
    form.reset(
      {
        ...partRawValue,
        id: { value: partRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PartFormDefaults {
    return {
      id: null,
      images: [],
    };
  }
}
