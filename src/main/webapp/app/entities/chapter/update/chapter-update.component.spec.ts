import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IImage } from 'app/entities/image/image.model';
import { ImageService } from 'app/entities/image/service/image.service';
import { IPart } from 'app/entities/part/part.model';
import { PartService } from 'app/entities/part/service/part.service';
import { IChapter } from '../chapter.model';
import { ChapterService } from '../service/chapter.service';
import { ChapterFormService } from './chapter-form.service';

import { ChapterUpdateComponent } from './chapter-update.component';

describe('Chapter Management Update Component', () => {
  let comp: ChapterUpdateComponent;
  let fixture: ComponentFixture<ChapterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let chapterFormService: ChapterFormService;
  let chapterService: ChapterService;
  let imageService: ImageService;
  let partService: PartService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ChapterUpdateComponent],
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
      .overrideTemplate(ChapterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ChapterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    chapterFormService = TestBed.inject(ChapterFormService);
    chapterService = TestBed.inject(ChapterService);
    imageService = TestBed.inject(ImageService);
    partService = TestBed.inject(PartService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Image query and add missing value', () => {
      const chapter: IChapter = { id: 456 };
      const images: IImage[] = [{ id: 11888 }];
      chapter.images = images;

      const imageCollection: IImage[] = [{ id: 31524 }];
      jest.spyOn(imageService, 'query').mockReturnValue(of(new HttpResponse({ body: imageCollection })));
      const additionalImages = [...images];
      const expectedCollection: IImage[] = [...additionalImages, ...imageCollection];
      jest.spyOn(imageService, 'addImageToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ chapter });
      comp.ngOnInit();

      expect(imageService.query).toHaveBeenCalled();
      expect(imageService.addImageToCollectionIfMissing).toHaveBeenCalledWith(
        imageCollection,
        ...additionalImages.map(expect.objectContaining),
      );
      expect(comp.imagesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Part query and add missing value', () => {
      const chapter: IChapter = { id: 456 };
      const part: IPart = { id: 7041 };
      chapter.part = part;

      const partCollection: IPart[] = [{ id: 26663 }];
      jest.spyOn(partService, 'query').mockReturnValue(of(new HttpResponse({ body: partCollection })));
      const additionalParts = [part];
      const expectedCollection: IPart[] = [...additionalParts, ...partCollection];
      jest.spyOn(partService, 'addPartToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ chapter });
      comp.ngOnInit();

      expect(partService.query).toHaveBeenCalled();
      expect(partService.addPartToCollectionIfMissing).toHaveBeenCalledWith(
        partCollection,
        ...additionalParts.map(expect.objectContaining),
      );
      expect(comp.partsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const chapter: IChapter = { id: 456 };
      const image: IImage = { id: 4945 };
      chapter.images = [image];
      const part: IPart = { id: 4392 };
      chapter.part = part;

      activatedRoute.data = of({ chapter });
      comp.ngOnInit();

      expect(comp.imagesSharedCollection).toContain(image);
      expect(comp.partsSharedCollection).toContain(part);
      expect(comp.chapter).toEqual(chapter);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChapter>>();
      const chapter = { id: 123 };
      jest.spyOn(chapterFormService, 'getChapter').mockReturnValue(chapter);
      jest.spyOn(chapterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chapter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: chapter }));
      saveSubject.complete();

      // THEN
      expect(chapterFormService.getChapter).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(chapterService.update).toHaveBeenCalledWith(expect.objectContaining(chapter));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChapter>>();
      const chapter = { id: 123 };
      jest.spyOn(chapterFormService, 'getChapter').mockReturnValue({ id: null });
      jest.spyOn(chapterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chapter: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: chapter }));
      saveSubject.complete();

      // THEN
      expect(chapterFormService.getChapter).toHaveBeenCalled();
      expect(chapterService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IChapter>>();
      const chapter = { id: 123 };
      jest.spyOn(chapterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ chapter });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(chapterService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareImage', () => {
      it('Should forward to imageService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(imageService, 'compareImage');
        comp.compareImage(entity, entity2);
        expect(imageService.compareImage).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePart', () => {
      it('Should forward to partService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(partService, 'comparePart');
        comp.comparePart(entity, entity2);
        expect(partService.comparePart).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
