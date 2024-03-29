import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IScene, Scene } from 'app/shared/model/scene.model';
import { SceneService } from './scene.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICover } from 'app/shared/model/cover.model';
import { IChapter } from 'app/shared/model/chapter.model';
import { ChapterService } from 'app/entities/chapter/chapter.service';
import { Regex } from 'app/shared/util/datetime-regex';

type SelectableEntity = ICover | IChapter;

@Component({
  selector: 'jhi-scene-update',
  templateUrl: './scene-update.component.html',
})
export class SceneUpdateComponent implements OnInit {
  isSaving = false;
  chapters: IChapter[] = [];
  datetimeFormat = Regex.datetimeFormat;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    number: [null, [Validators.required]],
    text: [null, [Validators.required]],
    timeStart: [],
    dateStart: [],
    chapterId: [null, [Validators.required]],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected sceneService: SceneService,
    protected chapterService: ChapterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scene }) => {
      this.updateForm(scene);

      this.getChapters(scene);
    });
  }

  getChapters(scene: IScene): void {
    this.chapterService.query().subscribe((res: HttpResponse<IChapter[]>) => (this.chapters = res.body || []));
    this.getDefaultChapter(scene);
  }

  getDefaultChapter(scene: IScene): void {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params['chapterId']) {
        scene.chapterId = Number(params['chapterId']);
        this.updateForm(scene);
      }
    });
  }

  updateForm(scene: IScene): void {
    this.editForm.patchValue({
      id: scene.id,
      name: scene.name,
      number: scene.number,
      text: scene.text,
      timeStart: String(scene.timestampStart).substring(11, 19),
      dateStart: String(scene.timestampStart).substring(0, 10),
      chapterId: scene.chapterId !== 0 ? scene.chapterId : null,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('nidAuxHistoiresApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const scene = this.createFromForm();
    if (scene.id !== undefined && scene.id !== 0) {
      this.subscribeToSaveResponse(this.sceneService.update(scene));
    } else {
      this.subscribeToSaveResponse(this.sceneService.create(scene));
    }
  }

  private createFromForm(): any {
    return {
      ...new Scene(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      number: this.editForm.get(['number'])!.value,
      text: this.editForm.get(['text'])!.value,
      timestampStart: this.formFieldToDate(),
      chapterId: this.editForm.get(['chapterId'])!.value,
    };
  }

  private formFieldToDate(): Date | undefined {
    const date = this.editForm.get(['dateStart'])!.value;
    const time = this.editForm.get(['timeStart'])!.value;
    const dateStr = date + 'T' + time + '.000+0000';
    return !isNaN(Date.parse(dateStr)) ? new Date(dateStr) : undefined;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScene>>): void {
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
