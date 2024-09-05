import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBook, Book } from 'app/shared/model/book.model';
import { BookService } from './book.service';
import { ICover } from 'app/shared/model/cover.model';
import { CoverService } from 'app/entities/cover/cover.service';

@Component({
  selector: 'jhi-book-update',
  templateUrl: './book-update.component.html',
})
export class BookUpdateComponent implements OnInit {
  isSaving = false;
  covers: ICover[] = [];

  editForm = this.fb.group({
    id: [],
    name: [Validators.required],
    authorId: [Validators.required],
    description: [],
    coverId: [],
  });

  constructor(
    protected bookService: BookService,
    protected coverService: CoverService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ book }) => {
      this.updateForm(book);

      this.coverService.query().subscribe((res: HttpResponse<ICover[]>) => (this.covers = res.body || []));
    });
  }

  updateForm(book: IBook): void {
    this.editForm.patchValue({
      id: book.id,
      name: book.name,
      authorId: book.authorId,
      description: book.description,
      coverId: book.coverId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const book = this.createFromForm();
    if (book.id !== undefined && book.id !== 0) {
      this.subscribeToSaveResponse(this.bookService.update(book));
    } else {
      delete book.id;
      this.subscribeToSaveResponse(this.bookService.create(book));
    }
  }

  private createFromForm(): any {
    return {
      ...new Book(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      authorId: this.editForm.get(['authorId'])!.value,
      description: this.editForm.get(['description'])!.value,
      coverId: this.editForm.get(['coverId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBook>>): void {
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

  trackById(index: number, item: ICover): any {
    return item.id;
  }

  getSelected(selectedVals: ICover[], option: ICover): ICover {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
