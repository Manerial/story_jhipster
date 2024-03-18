import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IImage } from 'app/entities/image/image.model';
import { ImageService } from 'app/entities/image/service/image.service';
import { IChapter } from 'app/entities/chapter/chapter.model';
import { ChapterService } from 'app/entities/chapter/service/chapter.service';
import { IScene } from '../scene.model';
import { SceneService } from '../service/scene.service';
import { SceneFormService } from './scene-form.service';

import { SceneUpdateComponent } from './scene-update.component';

describe('Scene Management Update Component', () => {
  let comp: SceneUpdateComponent;
  let fixture: ComponentFixture<SceneUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sceneFormService: SceneFormService;
  let sceneService: SceneService;
  let imageService: ImageService;
  let chapterService: ChapterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), SceneUpdateComponent],
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
      .overrideTemplate(SceneUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SceneUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sceneFormService = TestBed.inject(SceneFormService);
    sceneService = TestBed.inject(SceneService);
    imageService = TestBed.inject(ImageService);
    chapterService = TestBed.inject(ChapterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Image query and add missing value', () => {
      const scene: IScene = { id: 456 };
      const images: IImage[] = [{ id: 30683 }];
      scene.images = images;

      const imageCollection: IImage[] = [{ id: 3282 }];
      jest.spyOn(imageService, 'query').mockReturnValue(of(new HttpResponse({ body: imageCollection })));
      const additionalImages = [...images];
      const expectedCollection: IImage[] = [...additionalImages, ...imageCollection];
      jest.spyOn(imageService, 'addImageToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ scene });
      comp.ngOnInit();

      expect(imageService.query).toHaveBeenCalled();
      expect(imageService.addImageToCollectionIfMissing).toHaveBeenCalledWith(
        imageCollection,
        ...additionalImages.map(expect.objectContaining),
      );
      expect(comp.imagesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Chapter query and add missing value', () => {
      const scene: IScene = { id: 456 };
      const chapter: IChapter = { id: 188 };
      scene.chapter = chapter;

      const chapterCollection: IChapter[] = [{ id: 15543 }];
      jest.spyOn(chapterService, 'query').mockReturnValue(of(new HttpResponse({ body: chapterCollection })));
      const additionalChapters = [chapter];
      const expectedCollection: IChapter[] = [...additionalChapters, ...chapterCollection];
      jest.spyOn(chapterService, 'addChapterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ scene });
      comp.ngOnInit();

      expect(chapterService.query).toHaveBeenCalled();
      expect(chapterService.addChapterToCollectionIfMissing).toHaveBeenCalledWith(
        chapterCollection,
        ...additionalChapters.map(expect.objectContaining),
      );
      expect(comp.chaptersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const scene: IScene = { id: 456 };
      const image: IImage = { id: 18956 };
      scene.images = [image];
      const chapter: IChapter = { id: 18355 };
      scene.chapter = chapter;

      activatedRoute.data = of({ scene });
      comp.ngOnInit();

      expect(comp.imagesSharedCollection).toContain(image);
      expect(comp.chaptersSharedCollection).toContain(chapter);
      expect(comp.scene).toEqual(scene);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IScene>>();
      const scene = { id: 123 };
      jest.spyOn(sceneFormService, 'getScene').mockReturnValue(scene);
      jest.spyOn(sceneService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ scene });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: scene }));
      saveSubject.complete();

      // THEN
      expect(sceneFormService.getScene).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sceneService.update).toHaveBeenCalledWith(expect.objectContaining(scene));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IScene>>();
      const scene = { id: 123 };
      jest.spyOn(sceneFormService, 'getScene').mockReturnValue({ id: null });
      jest.spyOn(sceneService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ scene: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: scene }));
      saveSubject.complete();

      // THEN
      expect(sceneFormService.getScene).toHaveBeenCalled();
      expect(sceneService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IScene>>();
      const scene = { id: 123 };
      jest.spyOn(sceneService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ scene });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sceneService.update).toHaveBeenCalled();
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

    describe('compareChapter', () => {
      it('Should forward to chapterService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(chapterService, 'compareChapter');
        comp.compareChapter(entity, entity2);
        expect(chapterService.compareChapter).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
