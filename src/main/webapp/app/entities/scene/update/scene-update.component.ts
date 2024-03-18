import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IImage } from 'app/entities/image/image.model';
import { ImageService } from 'app/entities/image/service/image.service';
import { IChapter } from 'app/entities/chapter/chapter.model';
import { ChapterService } from 'app/entities/chapter/service/chapter.service';
import { SceneService } from '../service/scene.service';
import { IScene } from '../scene.model';
import { SceneFormService, SceneFormGroup } from './scene-form.service';

@Component({
  standalone: true,
  selector: 'jhi-scene-update',
  templateUrl: './scene-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SceneUpdateComponent implements OnInit {
  isSaving = false;
  scene: IScene | null = null;

  imagesSharedCollection: IImage[] = [];
  chaptersSharedCollection: IChapter[] = [];

  editForm: SceneFormGroup = this.sceneFormService.createSceneFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected sceneService: SceneService,
    protected sceneFormService: SceneFormService,
    protected imageService: ImageService,
    protected chapterService: ChapterService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareImage = (o1: IImage | null, o2: IImage | null): boolean => this.imageService.compareImage(o1, o2);

  compareChapter = (o1: IChapter | null, o2: IChapter | null): boolean => this.chapterService.compareChapter(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scene }) => {
      this.scene = scene;
      if (scene) {
        this.updateForm(scene);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('nidAuxHistoiresApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const scene = this.sceneFormService.getScene(this.editForm);
    if (scene.id !== null) {
      this.subscribeToSaveResponse(this.sceneService.update(scene));
    } else {
      this.subscribeToSaveResponse(this.sceneService.create(scene));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScene>>): void {
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

  protected updateForm(scene: IScene): void {
    this.scene = scene;
    this.sceneFormService.resetForm(this.editForm, scene);

    this.imagesSharedCollection = this.imageService.addImageToCollectionIfMissing<IImage>(
      this.imagesSharedCollection,
      ...(scene.images ?? []),
    );
    this.chaptersSharedCollection = this.chapterService.addChapterToCollectionIfMissing<IChapter>(
      this.chaptersSharedCollection,
      scene.chapter,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.imageService
      .query()
      .pipe(map((res: HttpResponse<IImage[]>) => res.body ?? []))
      .pipe(map((images: IImage[]) => this.imageService.addImageToCollectionIfMissing<IImage>(images, ...(this.scene?.images ?? []))))
      .subscribe((images: IImage[]) => (this.imagesSharedCollection = images));

    this.chapterService
      .query()
      .pipe(map((res: HttpResponse<IChapter[]>) => res.body ?? []))
      .pipe(map((chapters: IChapter[]) => this.chapterService.addChapterToCollectionIfMissing<IChapter>(chapters, this.scene?.chapter)))
      .subscribe((chapters: IChapter[]) => (this.chaptersSharedCollection = chapters));
  }
}
