import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IImage } from 'app/entities/image/image.model';
import { ImageService } from 'app/entities/image/service/image.service';
import { IPart } from 'app/entities/part/part.model';
import { PartService } from 'app/entities/part/service/part.service';
import { ChapterService } from '../service/chapter.service';
import { IChapter } from '../chapter.model';
import { ChapterFormService, ChapterFormGroup } from './chapter-form.service';

@Component({
  standalone: true,
  selector: 'jhi-chapter-update',
  templateUrl: './chapter-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ChapterUpdateComponent implements OnInit {
  isSaving = false;
  chapter: IChapter | null = null;

  imagesSharedCollection: IImage[] = [];
  partsSharedCollection: IPart[] = [];

  editForm: ChapterFormGroup = this.chapterFormService.createChapterFormGroup();

  constructor(
    protected chapterService: ChapterService,
    protected chapterFormService: ChapterFormService,
    protected imageService: ImageService,
    protected partService: PartService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareImage = (o1: IImage | null, o2: IImage | null): boolean => this.imageService.compareImage(o1, o2);

  comparePart = (o1: IPart | null, o2: IPart | null): boolean => this.partService.comparePart(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chapter }) => {
      this.chapter = chapter;
      if (chapter) {
        this.updateForm(chapter);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chapter = this.chapterFormService.getChapter(this.editForm);
    if (chapter.id !== null) {
      this.subscribeToSaveResponse(this.chapterService.update(chapter));
    } else {
      this.subscribeToSaveResponse(this.chapterService.create(chapter));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChapter>>): void {
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

  protected updateForm(chapter: IChapter): void {
    this.chapter = chapter;
    this.chapterFormService.resetForm(this.editForm, chapter);

    this.imagesSharedCollection = this.imageService.addImageToCollectionIfMissing<IImage>(
      this.imagesSharedCollection,
      ...(chapter.images ?? []),
    );
    this.partsSharedCollection = this.partService.addPartToCollectionIfMissing<IPart>(this.partsSharedCollection, chapter.part);
  }

  protected loadRelationshipsOptions(): void {
    this.imageService
      .query()
      .pipe(map((res: HttpResponse<IImage[]>) => res.body ?? []))
      .pipe(map((images: IImage[]) => this.imageService.addImageToCollectionIfMissing<IImage>(images, ...(this.chapter?.images ?? []))))
      .subscribe((images: IImage[]) => (this.imagesSharedCollection = images));

    this.partService
      .query()
      .pipe(map((res: HttpResponse<IPart[]>) => res.body ?? []))
      .pipe(map((parts: IPart[]) => this.partService.addPartToCollectionIfMissing<IPart>(parts, this.chapter?.part)))
      .subscribe((parts: IPart[]) => (this.partsSharedCollection = parts));
  }
}
