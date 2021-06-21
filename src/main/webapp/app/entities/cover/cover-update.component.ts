import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICover, Cover } from 'app/shared/model/cover.model';
import { CoverService } from './cover.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-cover-update',
  templateUrl: './cover-update.component.html',
})
export class CoverUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    picture: [null, [Validators.required]],
    pictureContentType: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected coverService: CoverService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cover }) => {
      this.updateForm(cover);
    });
  }

  updateForm(cover: ICover): void {
    this.editForm.patchValue({
      id: cover.id,
      name: cover.name,
      picture: cover.picture,
      pictureContentType: cover.pictureContentType,
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cover = this.createFromForm();
    if (cover.id !== undefined && cover.id !== 0) {
      this.subscribeToSaveResponse(this.coverService.update(cover));
    } else {
      delete cover.id;
      this.subscribeToSaveResponse(this.coverService.create(cover));
    }
  }

  private createFromForm(): any {
    return {
      ...new Cover(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      pictureContentType: this.editForm.get(['pictureContentType'])!.value,
      picture: this.editForm.get(['picture'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICover>>): void {
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
}
