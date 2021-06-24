import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IChapter, Chapter } from 'app/shared/model/chapter.model';
import { ChapterService } from './chapter.service';
import { ICover } from 'app/shared/model/cover.model';
import { IPart } from 'app/shared/model/part.model';
import { PartService } from 'app/entities/part/part.service';

type SelectableEntity = ICover | IPart;

@Component({
  selector: 'jhi-chapter-update',
  templateUrl: './chapter-update.component.html',
})
export class ChapterUpdateComponent implements OnInit {
  isSaving = false;
  parts: IPart[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    number: [null, [Validators.required]],
    partId: [null, [Validators.required]],
  });

  constructor(
    protected chapterService: ChapterService,
    protected partService: PartService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chapter }) => {
      this.updateForm(chapter);
      this.getParts(chapter);
    });
  }

  getParts(chapter: IChapter): void {
    this.partService.query().subscribe((res: HttpResponse<IPart[]>) => (this.parts = res.body || []));
    this.getDefaultPart(chapter);
  }

  getDefaultPart(chapter: IChapter): void {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params['partId']) {
        chapter.partId = Number(params['partId']);
        this.updateForm(chapter);
      }
    });
  }

  updateForm(chapter: IChapter): void {
    this.editForm.patchValue({
      id: chapter.id,
      name: chapter.name,
      description: chapter.description,
      number: chapter.number,
      partId: chapter.partId !== 0 ? chapter.partId : null,
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
