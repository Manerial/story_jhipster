import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IBook } from 'app/entities/book/book.model';
import { BookService } from 'app/entities/book/service/book.service';
import { IChapter } from 'app/entities/chapter/chapter.model';
import { ChapterService } from 'app/entities/chapter/service/chapter.service';
import { IBookStatus } from '../book-status.model';
import { BookStatusService } from '../service/book-status.service';
import { BookStatusFormService } from './book-status-form.service';

import { BookStatusUpdateComponent } from './book-status-update.component';

describe('BookStatus Management Update Component', () => {
  let comp: BookStatusUpdateComponent;
  let fixture: ComponentFixture<BookStatusUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bookStatusFormService: BookStatusFormService;
  let bookStatusService: BookStatusService;
  let bookService: BookService;
  let chapterService: ChapterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), BookStatusUpdateComponent],
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
      .overrideTemplate(BookStatusUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BookStatusUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bookStatusFormService = TestBed.inject(BookStatusFormService);
    bookStatusService = TestBed.inject(BookStatusService);
    bookService = TestBed.inject(BookService);
    chapterService = TestBed.inject(ChapterService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Book query and add missing value', () => {
      const bookStatus: IBookStatus = { id: 456 };
      const book: IBook = { id: 5645 };
      bookStatus.book = book;

      const bookCollection: IBook[] = [{ id: 14220 }];
      jest.spyOn(bookService, 'query').mockReturnValue(of(new HttpResponse({ body: bookCollection })));
      const additionalBooks = [book];
      const expectedCollection: IBook[] = [...additionalBooks, ...bookCollection];
      jest.spyOn(bookService, 'addBookToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bookStatus });
      comp.ngOnInit();

      expect(bookService.query).toHaveBeenCalled();
      expect(bookService.addBookToCollectionIfMissing).toHaveBeenCalledWith(
        bookCollection,
        ...additionalBooks.map(expect.objectContaining),
      );
      expect(comp.booksSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Chapter query and add missing value', () => {
      const bookStatus: IBookStatus = { id: 456 };
      const curentChapter: IChapter = { id: 18807 };
      bookStatus.curentChapter = curentChapter;

      const chapterCollection: IChapter[] = [{ id: 12920 }];
      jest.spyOn(chapterService, 'query').mockReturnValue(of(new HttpResponse({ body: chapterCollection })));
      const additionalChapters = [curentChapter];
      const expectedCollection: IChapter[] = [...additionalChapters, ...chapterCollection];
      jest.spyOn(chapterService, 'addChapterToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bookStatus });
      comp.ngOnInit();

      expect(chapterService.query).toHaveBeenCalled();
      expect(chapterService.addChapterToCollectionIfMissing).toHaveBeenCalledWith(
        chapterCollection,
        ...additionalChapters.map(expect.objectContaining),
      );
      expect(comp.chaptersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bookStatus: IBookStatus = { id: 456 };
      const book: IBook = { id: 3780 };
      bookStatus.book = book;
      const curentChapter: IChapter = { id: 27798 };
      bookStatus.curentChapter = curentChapter;

      activatedRoute.data = of({ bookStatus });
      comp.ngOnInit();

      expect(comp.booksSharedCollection).toContain(book);
      expect(comp.chaptersSharedCollection).toContain(curentChapter);
      expect(comp.bookStatus).toEqual(bookStatus);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBookStatus>>();
      const bookStatus = { id: 123 };
      jest.spyOn(bookStatusFormService, 'getBookStatus').mockReturnValue(bookStatus);
      jest.spyOn(bookStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bookStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bookStatus }));
      saveSubject.complete();

      // THEN
      expect(bookStatusFormService.getBookStatus).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(bookStatusService.update).toHaveBeenCalledWith(expect.objectContaining(bookStatus));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBookStatus>>();
      const bookStatus = { id: 123 };
      jest.spyOn(bookStatusFormService, 'getBookStatus').mockReturnValue({ id: null });
      jest.spyOn(bookStatusService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bookStatus: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bookStatus }));
      saveSubject.complete();

      // THEN
      expect(bookStatusFormService.getBookStatus).toHaveBeenCalled();
      expect(bookStatusService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBookStatus>>();
      const bookStatus = { id: 123 };
      jest.spyOn(bookStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bookStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bookStatusService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareBook', () => {
      it('Should forward to bookService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(bookService, 'compareBook');
        comp.compareBook(entity, entity2);
        expect(bookService.compareBook).toHaveBeenCalledWith(entity, entity2);
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
