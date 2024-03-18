import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IBook } from 'app/entities/book/book.model';
import { BookService } from 'app/entities/book/service/book.service';
import { IChapter } from 'app/entities/chapter/chapter.model';
import { ChapterService } from 'app/entities/chapter/service/chapter.service';
import { BookStatusService } from '../service/book-status.service';
import { IBookStatus } from '../book-status.model';
import { BookStatusFormService, BookStatusFormGroup } from './book-status-form.service';

@Component({
  standalone: true,
  selector: 'jhi-book-status-update',
  templateUrl: './book-status-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class BookStatusUpdateComponent implements OnInit {
  isSaving = false;
  bookStatus: IBookStatus | null = null;

  booksSharedCollection: IBook[] = [];
  chaptersSharedCollection: IChapter[] = [];

  editForm: BookStatusFormGroup = this.bookStatusFormService.createBookStatusFormGroup();

  constructor(
    protected bookStatusService: BookStatusService,
    protected bookStatusFormService: BookStatusFormService,
    protected bookService: BookService,
    protected chapterService: ChapterService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareBook = (o1: IBook | null, o2: IBook | null): boolean => this.bookService.compareBook(o1, o2);

  compareChapter = (o1: IChapter | null, o2: IChapter | null): boolean => this.chapterService.compareChapter(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookStatus }) => {
      this.bookStatus = bookStatus;
      if (bookStatus) {
        this.updateForm(bookStatus);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bookStatus = this.bookStatusFormService.getBookStatus(this.editForm);
    if (bookStatus.id !== null) {
      this.subscribeToSaveResponse(this.bookStatusService.update(bookStatus));
    } else {
      this.subscribeToSaveResponse(this.bookStatusService.create(bookStatus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBookStatus>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(bookStatus: IBookStatus): void {
    this.bookStatus = bookStatus;
    this.bookStatusFormService.resetForm(this.editForm, bookStatus);

    this.booksSharedCollection = this.bookService.addBookToCollectionIfMissing<IBook>(this.booksSharedCollection, bookStatus.book);
    this.chaptersSharedCollection = this.chapterService.addChapterToCollectionIfMissing<IChapter>(
      this.chaptersSharedCollection,
      bookStatus.curentChapter,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.bookService
      .query()
      .pipe(map((res: HttpResponse<IBook[]>) => res.body ?? []))
      .pipe(map((books: IBook[]) => this.bookService.addBookToCollectionIfMissing<IBook>(books, this.bookStatus?.book)))
      .subscribe((books: IBook[]) => (this.booksSharedCollection = books));

    this.chapterService
      .query()
      .pipe(map((res: HttpResponse<IChapter[]>) => res.body ?? []))
      .pipe(
        map((chapters: IChapter[]) =>
          this.chapterService.addChapterToCollectionIfMissing<IChapter>(chapters, this.bookStatus?.curentChapter),
        ),
      )
      .subscribe((chapters: IChapter[]) => (this.chaptersSharedCollection = chapters));
  }
}
