import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBonus, NewBonus } from '../bonus.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBonus for edit and NewBonusFormGroupInput for create.
 */
type BonusFormGroupInput = IBonus | PartialWithRequiredKeyOf<NewBonus>;

type BonusFormDefaults = Pick<NewBonus, 'id'>;

type BonusFormGroupContent = {
  id: FormControl<IBonus['id'] | NewBonus['id']>;
  name: FormControl<IBonus['name']>;
  extension: FormControl<IBonus['extension']>;
};

export type BonusFormGroup = FormGroup<BonusFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BonusFormService {
  createBonusFormGroup(bonus: BonusFormGroupInput = { id: null }): BonusFormGroup {
    const bonusRawValue = {
      ...this.getFormDefaults(),
      ...bonus,
    };
    return new FormGroup<BonusFormGroupContent>({
      id: new FormControl(
        { value: bonusRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(bonusRawValue.name),
      extension: new FormControl(bonusRawValue.extension),
    });
  }

  getBonus(form: BonusFormGroup): IBonus | NewBonus {
    return form.getRawValue() as IBonus | NewBonus;
  }

  resetForm(form: BonusFormGroup, bonus: BonusFormGroupInput): void {
    const bonusRawValue = { ...this.getFormDefaults(), ...bonus };
    form.reset(
      {
        ...bonusRawValue,
        id: { value: bonusRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): BonusFormDefaults {
    return {
      id: null,
    };
  }
}
