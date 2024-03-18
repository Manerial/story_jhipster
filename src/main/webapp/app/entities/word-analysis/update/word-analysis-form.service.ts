import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IWordAnalysis, NewWordAnalysis } from '../word-analysis.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IWordAnalysis for edit and NewWordAnalysisFormGroupInput for create.
 */
type WordAnalysisFormGroupInput = IWordAnalysis | PartialWithRequiredKeyOf<NewWordAnalysis>;

type WordAnalysisFormDefaults = Pick<NewWordAnalysis, 'id'>;

type WordAnalysisFormGroupContent = {
  id: FormControl<IWordAnalysis['id'] | NewWordAnalysis['id']>;
  type: FormControl<IWordAnalysis['type']>;
  name: FormControl<IWordAnalysis['name']>;
  analysis: FormControl<IWordAnalysis['analysis']>;
};

export type WordAnalysisFormGroup = FormGroup<WordAnalysisFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class WordAnalysisFormService {
  createWordAnalysisFormGroup(wordAnalysis: WordAnalysisFormGroupInput = { id: null }): WordAnalysisFormGroup {
    const wordAnalysisRawValue = {
      ...this.getFormDefaults(),
      ...wordAnalysis,
    };
    return new FormGroup<WordAnalysisFormGroupContent>({
      id: new FormControl(
        { value: wordAnalysisRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      type: new FormControl(wordAnalysisRawValue.type),
      name: new FormControl(wordAnalysisRawValue.name),
      analysis: new FormControl(wordAnalysisRawValue.analysis),
    });
  }

  getWordAnalysis(form: WordAnalysisFormGroup): IWordAnalysis | NewWordAnalysis {
    return form.getRawValue() as IWordAnalysis | NewWordAnalysis;
  }

  resetForm(form: WordAnalysisFormGroup, wordAnalysis: WordAnalysisFormGroupInput): void {
    const wordAnalysisRawValue = { ...this.getFormDefaults(), ...wordAnalysis };
    form.reset(
      {
        ...wordAnalysisRawValue,
        id: { value: wordAnalysisRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): WordAnalysisFormDefaults {
    return {
      id: null,
    };
  }
}
