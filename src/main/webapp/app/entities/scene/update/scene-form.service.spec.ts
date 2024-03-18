import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../scene.test-samples';

import { SceneFormService } from './scene-form.service';

describe('Scene Form Service', () => {
  let service: SceneFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SceneFormService);
  });

  describe('Service methods', () => {
    describe('createSceneFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSceneFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            number: expect.any(Object),
            text: expect.any(Object),
            timestampStart: expect.any(Object),
            images: expect.any(Object),
            chapter: expect.any(Object),
          }),
        );
      });

      it('passing IScene should create a new form with FormGroup', () => {
        const formGroup = service.createSceneFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            number: expect.any(Object),
            text: expect.any(Object),
            timestampStart: expect.any(Object),
            images: expect.any(Object),
            chapter: expect.any(Object),
          }),
        );
      });
    });

    describe('getScene', () => {
      it('should return NewScene for default Scene initial value', () => {
        const formGroup = service.createSceneFormGroup(sampleWithNewData);

        const scene = service.getScene(formGroup) as any;

        expect(scene).toMatchObject(sampleWithNewData);
      });

      it('should return NewScene for empty Scene initial value', () => {
        const formGroup = service.createSceneFormGroup();

        const scene = service.getScene(formGroup) as any;

        expect(scene).toMatchObject({});
      });

      it('should return IScene', () => {
        const formGroup = service.createSceneFormGroup(sampleWithRequiredData);

        const scene = service.getScene(formGroup) as any;

        expect(scene).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IScene should not enable id FormControl', () => {
        const formGroup = service.createSceneFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewScene should disable id FormControl', () => {
        const formGroup = service.createSceneFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
