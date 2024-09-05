import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPart, Part } from 'app/shared/model/part.model';
import { PartService } from './part.service';
import { IImage } from 'app/shared/model/image.model';
import { ImageService } from 'app/entities/image/image.service';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book/book.service';

type SelectableEntity = IImage | IBook;

@Component({
  selector: 'jhi-part-update',
  templateUrl: './part-update.component.html',
})
export class PartUpdateComponent implements OnInit {
  isSaving = false;
  images: IImage[] = [];
  books: IBook[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    number: [],
    images: [],
    bookId: [],
  });

  constructor(
    protected partService: PartService,
    protected imageService: ImageService,
    protected bookService: BookService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ part }) => {
      this.updateForm(part);

      this.imageService.query().subscribe((res: HttpResponse<IImage[]>) => (this.images = res.body || []));

      this.bookService.query().subscribe((res: HttpResponse<IBook[]>) => (this.books = res.body || []));
    });
  }

  updateForm(part: IPart): void {
    this.editForm.patchValue({
      id: part.id,
      name: part.name,
      description: part.description,
      number: part.number,
      images: part.images,
      bookId: part.bookId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const part = this.createFromForm();
    if (part.id !== undefined) {
      this.subscribeToSaveResponse(this.partService.update(part));
    } else {
      this.subscribeToSaveResponse(this.partService.create(part));
    }
  }

  private createFromForm(): IPart {
    return {
      ...new Part(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      number: this.editForm.get(['number'])!.value,
      images: this.editForm.get(['images'])!.value,
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

  getSelected(selectedVals: IImage[], option: IImage): IImage {
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
