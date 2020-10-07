import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NidAuxHistoiresTestModule } from '../../../test.module';
import { SceneUpdateComponent } from 'app/entities/scene/scene-update.component';
import { SceneService } from 'app/entities/scene/scene.service';
import { Scene } from 'app/shared/model/scene.model';

describe('Component Tests', () => {
  describe('Scene Management Update Component', () => {
    let comp: SceneUpdateComponent;
    let fixture: ComponentFixture<SceneUpdateComponent>;
    let service: SceneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NidAuxHistoiresTestModule],
        declarations: [SceneUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SceneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SceneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SceneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Scene(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Scene();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
