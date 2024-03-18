import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILibrary, Library } from 'app/shared/model/library.model';
import { LibraryService } from './library.service';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book/book.service';
import { IChapter } from 'app/shared/model/chapter.model';
import { ChapterService } from 'app/entities/chapter/chapter.service';

type SelectableEntity = IBook | IChapter;

@Component({
  selector: 'jhi-library-update',
  templateUrl: './library-update.component.html',
})
export class LibraryUpdateComponent implements OnInit {
  isSaving = false;
  books: IBook[] = [];
  chapters: IChapter[] = [];

  editForm = this.fb.group({
    id: [],
    finished: [],
    favorit: [],
    bookId: [],
    curentChapterId: [],
  });

  constructor(
    protected libraryService: LibraryService,
    protected bookService: BookService,
    protected chapterService: ChapterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ library }) => {
      this.updateForm(library);

      this.bookService.query().subscribe((res: HttpResponse<IBook[]>) => (this.books = res.body || []));

      this.chapterService.query().subscribe((res: HttpResponse<IChapter[]>) => (this.chapters = res.body || []));
    });
  }

  updateForm(library: ILibrary): void {
    this.editForm.patchValue({
      id: library.id,
      finished: library.finished,
      favorit: library.favorit,
      bookId: library.bookId,
      curentChapterId: library.curentChapterId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const library = this.createFromForm();
    if (library.id !== undefined) {
      this.subscribeToSaveResponse(this.libraryService.update(library));
    } else {
      this.subscribeToSaveResponse(this.libraryService.create(library));
    }
  }

  private createFromForm(): ILibrary {
    return {
      ...new Library(),
      id: this.editForm.get(['id'])!.value,
      finished: this.editForm.get(['finished'])!.value,
      favorit: this.editForm.get(['favorit'])!.value,
      bookId: this.editForm.get(['bookId'])!.value,
      curentChapterId: this.editForm.get(['curentChapterId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILibrary>>): void {
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
