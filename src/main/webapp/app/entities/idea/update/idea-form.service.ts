import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IIdea, NewIdea } from '../idea.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIdea for edit and NewIdeaFormGroupInput for create.
 */
type IdeaFormGroupInput = IIdea | PartialWithRequiredKeyOf<NewIdea>;

type IdeaFormDefaults = Pick<NewIdea, 'id'>;

type IdeaFormGroupContent = {
  id: FormControl<IIdea['id'] | NewIdea['id']>;
  type: FormControl<IIdea['type']>;
  value: FormControl<IIdea['value']>;
  complement: FormControl<IIdea['complement']>;
};

export type IdeaFormGroup = FormGroup<IdeaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IdeaFormService {
  createIdeaFormGroup(idea: IdeaFormGroupInput = { id: null }): IdeaFormGroup {
    const ideaRawValue = {
      ...this.getFormDefaults(),
      ...idea,
    };
    return new FormGroup<IdeaFormGroupContent>({
      id: new FormControl(
        { value: ideaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      type: new FormControl(ideaRawValue.type),
      value: new FormControl(ideaRawValue.value),
      complement: new FormControl(ideaRawValue.complement),
    });
  }

  getIdea(form: IdeaFormGroup): IIdea | NewIdea {
    return form.getRawValue() as IIdea | NewIdea;
  }

  resetForm(form: IdeaFormGroup, idea: IdeaFormGroupInput): void {
    const ideaRawValue = { ...this.getFormDefaults(), ...idea };
    form.reset(
      {
        ...ideaRawValue,
        id: { value: ideaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): IdeaFormDefaults {
    return {
      id: null,
    };
  }
}
