import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IChapter, Chapter } from 'app/shared/model/chapter.model';
import { ChapterService } from './chapter.service';
import { IImage } from 'app/shared/model/image.model';
import { ImageService } from 'app/entities/image/image.service';
import { IPart, Part } from 'app/shared/model/part.model';
import { PartService } from 'app/entities/part/part.service';

type SelectableEntity = IImage | IPart;

@Component({
  selector: 'jhi-chapter-update',
  templateUrl: './chapter-update.component.html',
})
export class ChapterUpdateComponent implements OnInit {
  isSaving = false;
  images: IImage[] = [];
  parts: IPart[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    number: [],
    images: [],
    partId: [],
  });

  constructor(
    protected chapterService: ChapterService,
    protected imageService: ImageService,
    protected partService: PartService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chapter }) => {
      this.updateForm(chapter);

      this.imageService.query().subscribe((dataImage: HttpResponse<IImage[]>) => (this.images = dataImage.body || []));

      if (chapter.partId !== undefined && chapter.partId !== null && chapter.partId !== 0) {
        this.partService.find(chapter.partId).subscribe((dataPart: HttpResponse<IPart>) => {
          const part = dataPart.body || new Part();
          this.partService.query({ bookId: part.bookId }).subscribe((res: HttpResponse<IPart[]>) => (this.parts = res.body || []));
        });
      } else {
        this.partService.query().subscribe((res: HttpResponse<IPart[]>) => (this.parts = res.body || []));
      }
    });
  }

  updateForm(chapter: IChapter): void {
    chapter.images.forEach(image => {
      image.picture = null;
      image.preview = null;
    });

    this.editForm.patchValue({
      id: chapter.id,
      name: chapter.name,
      description: chapter.description,
      number: chapter.number,
      images: chapter.images,
      partId: chapter.partId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chapter = this.createFromForm();
    if (chapter.id !== undefined && chapter.id !== 0) {
      this.subscribeToSaveResponse(this.chapterService.update(chapter));
    } else {
      delete chapter.id;
      this.subscribeToSaveResponse(this.chapterService.create(chapter));
    }
  }

  private createFromForm(): any {
    return {
      ...new Chapter(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      number: this.editForm.get(['number'])!.value,
      images: this.editForm.get(['images'])!.value,
      partId: this.editForm.get(['partId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChapter>>): void {
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
