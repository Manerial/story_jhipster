import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NidAuxHistoiresTestModule } from '../../../test.module';
import { WordAnalysisUpdateComponent } from 'app/entities/word-analysis/word-analysis-update.component';
import { WordAnalysisService } from 'app/entities/word-analysis/word-analysis.service';
import { WordAnalysis } from 'app/shared/model/word-analysis.model';

describe('Component Tests', () => {
  describe('WordAnalysis Management Update Component', () => {
    let comp: WordAnalysisUpdateComponent;
    let fixture: ComponentFixture<WordAnalysisUpdateComponent>;
    let service: WordAnalysisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NidAuxHistoiresTestModule],
        declarations: [WordAnalysisUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WordAnalysisUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WordAnalysisUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WordAnalysisService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WordAnalysis(123);
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
        const entity = new WordAnalysis();
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
