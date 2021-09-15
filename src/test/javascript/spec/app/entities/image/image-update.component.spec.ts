import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NidAuxHistoiresTestModule } from '../../../test.module';
import { CoverUpdateComponent } from 'app/entities/cover/cover-update.component';
import { CoverService } from 'app/entities/cover/cover.service';
import { Cover } from 'app/shared/model/cover.model';

describe('Component Tests', () => {
  describe('Image Management Update Component', () => {
    let comp: CoverUpdateComponent;
    let fixture: ComponentFixture<CoverUpdateComponent>;
    let service: CoverService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NidAuxHistoiresTestModule],
        declarations: [CoverUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CoverUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CoverUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CoverService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Cover(123);
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
        const entity = new Cover();
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
