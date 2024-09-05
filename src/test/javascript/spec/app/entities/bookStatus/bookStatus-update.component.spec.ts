import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NidAuxHistoiresTestModule } from '../../../test.module';
import { BookStatusUpdateComponent } from 'app/entities/bookStatus/bookStatus-update.component';
import { BookStatusService } from 'app/entities/bookStatus/bookStatus.service';
import { BookStatus } from 'app/shared/model/bookStatus.model';

describe('Component Tests', () => {
  describe('BookStatus Management Update Component', () => {
    let comp: BookStatusUpdateComponent;
    let fixture: ComponentFixture<BookStatusUpdateComponent>;
    let service: BookStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NidAuxHistoiresTestModule],
        declarations: [BookStatusUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BookStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BookStatus(123);
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
        const entity = new BookStatus();
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
