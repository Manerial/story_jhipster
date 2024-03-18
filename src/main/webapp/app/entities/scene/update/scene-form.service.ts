import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IScene, NewScene } from '../scene.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IScene for edit and NewSceneFormGroupInput for create.
 */
type SceneFormGroupInput = IScene | PartialWithRequiredKeyOf<NewScene>;

type SceneFormDefaults = Pick<NewScene, 'id' | 'images'>;

type SceneFormGroupContent = {
  id: FormControl<IScene['id'] | NewScene['id']>;
  name: FormControl<IScene['name']>;
  number: FormControl<IScene['number']>;
  text: FormControl<IScene['text']>;
  timestampStart: FormControl<IScene['timestampStart']>;
  images: FormControl<IScene['images']>;
  chapter: FormControl<IScene['chapter']>;
};

export type SceneFormGroup = FormGroup<SceneFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SceneFormService {
  createSceneFormGroup(scene: SceneFormGroupInput = { id: null }): SceneFormGroup {
    const sceneRawValue = {
      ...this.getFormDefaults(),
      ...scene,
    };
    return new FormGroup<SceneFormGroupContent>({
      id: new FormControl(
        { value: sceneRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(sceneRawValue.name),
      number: new FormControl(sceneRawValue.number),
      text: new FormControl(sceneRawValue.text),
      timestampStart: new FormControl(sceneRawValue.timestampStart),
      images: new FormControl(sceneRawValue.images ?? []),
      chapter: new FormControl(sceneRawValue.chapter),
    });
  }

  getScene(form: SceneFormGroup): IScene | NewScene {
    return form.getRawValue() as IScene | NewScene;
  }

  resetForm(form: SceneFormGroup, scene: SceneFormGroupInput): void {
    const sceneRawValue = { ...this.getFormDefaults(), ...scene };
    form.reset(
      {
        ...sceneRawValue,
        id: { value: sceneRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SceneFormDefaults {
    return {
      id: null,
      images: [],
    };
  }
}
