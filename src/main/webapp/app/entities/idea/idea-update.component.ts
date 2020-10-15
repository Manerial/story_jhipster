import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIdea, Idea } from 'app/shared/model/idea.model';
import { IdeaService } from './idea.service';

@Component({
  selector: 'jhi-idea-update',
  templateUrl: './idea-update.component.html',
})
export class IdeaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    type: [],
    value: [],
    complement: [],
  });

  constructor(protected ideaService: IdeaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ idea }) => {
      this.updateForm(idea);
    });
  }

  updateForm(idea: IIdea): void {
    this.editForm.patchValue({
      id: idea.id,
      type: idea.type,
      value: idea.value,
      complement: idea.complement,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const idea = this.createFromForm();
    if (idea.id !== undefined) {
      this.subscribeToSaveResponse(this.ideaService.update(idea));
    } else {
      this.subscribeToSaveResponse(this.ideaService.create(idea));
    }
  }

  private createFromForm(): IIdea {
    return {
      ...new Idea(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      value: this.editForm.get(['value'])!.value,
      complement: this.editForm.get(['complement'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIdea>>): void {
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
