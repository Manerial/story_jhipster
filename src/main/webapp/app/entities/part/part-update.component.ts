import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPart, Part } from 'app/shared/model/part.model';
import { PartService } from './part.service';
import { ICover } from 'app/shared/model/cover.model';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book/book.service';

type SelectableEntity = ICover | IBook;

@Component({
  selector: 'jhi-part-update',
  templateUrl: './part-update.component.html',
})
export class PartUpdateComponent implements OnInit {
  isSaving = false;
  books: IBook[] = [];

  editForm = this.fb.group({
    id: [],
    name: [Validators.required],
    description: [],
    number: [Validators.required],
    bookId: [Validators.required],
  });

  constructor(
    protected partService: PartService,
    protected bookService: BookService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ part }) => {
      this.updateForm(part);

      this.bookService.query().subscribe((res: HttpResponse<IBook[]>) => (this.books = res.body || []));
    });
  }

  updateForm(part: IPart): void {
    this.editForm.patchValue({
      id: part.id,
      name: part.name,
      description: part.description,
      number: part.number,
      bookId: part.bookId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const part = this.createFromForm();
    if (part.id !== undefined && part.id !== 0) {
      this.subscribeToSaveResponse(this.partService.update(part));
    } else {
      delete part.id;
      this.subscribeToSaveResponse(this.partService.create(part));
    }
  }

  private createFromForm(): any {
    return {
      ...new Part(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      number: this.editForm.get(['number'])!.value,
      bookId: this.editForm.get(['bookId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPart>>): void {
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
