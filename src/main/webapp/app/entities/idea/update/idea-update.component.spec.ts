import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IdeaService } from '../service/idea.service';
import { IIdea } from '../idea.model';
import { IdeaFormService } from './idea-form.service';

import { IdeaUpdateComponent } from './idea-update.component';

describe('Idea Management Update Component', () => {
  let comp: IdeaUpdateComponent;
  let fixture: ComponentFixture<IdeaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ideaFormService: IdeaFormService;
  let ideaService: IdeaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), IdeaUpdateComponent],
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
      .overrideTemplate(IdeaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IdeaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ideaFormService = TestBed.inject(IdeaFormService);
    ideaService = TestBed.inject(IdeaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const idea: IIdea = { id: 456 };

      activatedRoute.data = of({ idea });
      comp.ngOnInit();

      expect(comp.idea).toEqual(idea);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIdea>>();
      const idea = { id: 123 };
      jest.spyOn(ideaFormService, 'getIdea').mockReturnValue(idea);
      jest.spyOn(ideaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ idea });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: idea }));
      saveSubject.complete();

      // THEN
      expect(ideaFormService.getIdea).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ideaService.update).toHaveBeenCalledWith(expect.objectContaining(idea));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIdea>>();
      const idea = { id: 123 };
      jest.spyOn(ideaFormService, 'getIdea').mockReturnValue({ id: null });
      jest.spyOn(ideaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ idea: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: idea }));
      saveSubject.complete();

      // THEN
      expect(ideaFormService.getIdea).toHaveBeenCalled();
      expect(ideaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIdea>>();
      const idea = { id: 123 };
      jest.spyOn(ideaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ idea });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ideaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
