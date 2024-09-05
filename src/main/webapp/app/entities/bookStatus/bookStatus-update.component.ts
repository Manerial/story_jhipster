import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBookStatus, BookStatus } from 'app/shared/model/bookStatus.model';
import { BookStatusService } from './bookStatus.service';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book/book.service';
import { IChapter } from 'app/shared/model/chapter.model';
import { ChapterService } from 'app/entities/chapter/chapter.service';

type SelectableEntity = IBook | IChapter;

@Component({
  selector: 'jhi-library-update',
  templateUrl: './bookStatus-update.component.html',
})
export class BookStatusUpdateComponent implements OnInit {
  isSaving = false;
  books: IBook[] = [];
  chapters: IChapter[] = [];

  editForm = this.fb.group({
    id: [],
    finished: [],
    favorit: [],
    bookId: [Validators.required],
    curentChapterId: [],
  });

  constructor(
    protected bookStatusService: BookStatusService,
    protected bookService: BookService,
    protected chapterService: ChapterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookStatus }) => {
      this.updateForm(bookStatus);

      this.bookService.query().subscribe((res: HttpResponse<IBook[]>) => (this.books = res.body || []));

      this.chapterService.query().subscribe((res: HttpResponse<IChapter[]>) => (this.chapters = res.body || []));
    });
  }

  updateForm(bookStatus: IBookStatus): void {
    this.editForm.patchValue({
      id: bookStatus.id,
      finished: bookStatus.finished,
      favorit: bookStatus.favorit,
      bookId: bookStatus.bookId,
      curentChapterId: bookStatus.curentChapterId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bookStatus = this.createFromForm();
    if (bookStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.bookStatusService.update(bookStatus));
    } else {
      this.subscribeToSaveResponse(this.bookStatusService.create(bookStatus));
    }
  }

  private createFromForm(): any {
    return {
      ...new BookStatus(),
      id: this.editForm.get(['id'])!.value,
      finished: this.editForm.get(['finished'])!.value,
      favorit: this.editForm.get(['favorit'])!.value,
      bookId: this.editForm.get(['bookId'])!.value,
      curentChapterId: this.editForm.get(['curentChapterId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBookStatus>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
