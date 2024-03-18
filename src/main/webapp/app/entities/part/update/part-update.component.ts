import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IImage } from 'app/entities/image/image.model';
import { ImageService } from 'app/entities/image/service/image.service';
import { IBook } from 'app/entities/book/book.model';
import { BookService } from 'app/entities/book/service/book.service';
import { PartService } from '../service/part.service';
import { IPart } from '../part.model';
import { PartFormService, PartFormGroup } from './part-form.service';

@Component({
  standalone: true,
  selector: 'jhi-part-update',
  templateUrl: './part-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PartUpdateComponent implements OnInit {
  isSaving = false;
  part: IPart | null = null;

  imagesSharedCollection: IImage[] = [];
  booksSharedCollection: IBook[] = [];

  editForm: PartFormGroup = this.partFormService.createPartFormGroup();

  constructor(
    protected partService: PartService,
    protected partFormService: PartFormService,
    protected imageService: ImageService,
    protected bookService: BookService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareImage = (o1: IImage | null, o2: IImage | null): boolean => this.imageService.compareImage(o1, o2);

  compareBook = (o1: IBook | null, o2: IBook | null): boolean => this.bookService.compareBook(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ part }) => {
      this.part = part;
      if (part) {
        this.updateForm(part);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const part = this.partFormService.getPart(this.editForm);
    if (part.id !== null) {
      this.subscribeToSaveResponse(this.partService.update(part));
    } else {
      this.subscribeToSaveResponse(this.partService.create(part));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPart>>): void {
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

  protected updateForm(part: IPart): void {
    this.part = part;
    this.partFormService.resetForm(this.editForm, part);

    this.imagesSharedCollection = this.imageService.addImageToCollectionIfMissing<IImage>(
      this.imagesSharedCollection,
      ...(part.images ?? []),
    );
    this.booksSharedCollection = this.bookService.addBookToCollectionIfMissing<IBook>(this.booksSharedCollection, part.book);
  }

  protected loadRelationshipsOptions(): void {
    this.imageService
      .query()
      .pipe(map((res: HttpResponse<IImage[]>) => res.body ?? []))
      .pipe(map((images: IImage[]) => this.imageService.addImageToCollectionIfMissing<IImage>(images, ...(this.part?.images ?? []))))
      .subscribe((images: IImage[]) => (this.imagesSharedCollection = images));

    this.bookService
      .query()
      .pipe(map((res: HttpResponse<IBook[]>) => res.body ?? []))
      .pipe(map((books: IBook[]) => this.bookService.addBookToCollectionIfMissing<IBook>(books, this.part?.book)))
      .subscribe((books: IBook[]) => (this.booksSharedCollection = books));
  }
}
