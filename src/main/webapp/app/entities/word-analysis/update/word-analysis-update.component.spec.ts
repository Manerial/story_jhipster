import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { WordAnalysisService } from '../service/word-analysis.service';
import { IWordAnalysis } from '../word-analysis.model';
import { WordAnalysisFormService } from './word-analysis-form.service';

import { WordAnalysisUpdateComponent } from './word-analysis-update.component';

describe('WordAnalysis Management Update Component', () => {
  let comp: WordAnalysisUpdateComponent;
  let fixture: ComponentFixture<WordAnalysisUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let wordAnalysisFormService: WordAnalysisFormService;
  let wordAnalysisService: WordAnalysisService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), WordAnalysisUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(WordAnalysisUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WordAnalysisUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    wordAnalysisFormService = TestBed.inject(WordAnalysisFormService);
    wordAnalysisService = TestBed.inject(WordAnalysisService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const wordAnalysis: IWordAnalysis = { id: 456 };

      activatedRoute.data = of({ wordAnalysis });
      comp.ngOnInit();

      expect(comp.wordAnalysis).toEqual(wordAnalysis);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWordAnalysis>>();
      const wordAnalysis = { id: 123 };
      jest.spyOn(wordAnalysisFormService, 'getWordAnalysis').mockReturnValue(wordAnalysis);
      jest.spyOn(wordAnalysisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ wordAnalysis });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: wordAnalysis }));
      saveSubject.complete();

      // THEN
      expect(wordAnalysisFormService.getWordAnalysis).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(wordAnalysisService.update).toHaveBeenCalledWith(expect.objectContaining(wordAnalysis));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWordAnalysis>>();
      const wordAnalysis = { id: 123 };
      jest.spyOn(wordAnalysisFormService, 'getWordAnalysis').mockReturnValue({ id: null });
      jest.spyOn(wordAnalysisService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ wordAnalysis: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: wordAnalysis }));
      saveSubject.complete();

      // THEN
      expect(wordAnalysisFormService.getWordAnalysis).toHaveBeenCalled();
      expect(wordAnalysisService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWordAnalysis>>();
      const wordAnalysis = { id: 123 };
      jest.spyOn(wordAnalysisService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ wordAnalysis });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(wordAnalysisService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
