import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IIdea } from '../idea.model';
import { IdeaService } from '../service/idea.service';
import { IdeaFormService, IdeaFormGroup } from './idea-form.service';

@Component({
  standalone: true,
  selector: 'jhi-idea-update',
  templateUrl: './idea-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class IdeaUpdateComponent implements OnInit {
  isSaving = false;
  idea: IIdea | null = null;

  editForm: IdeaFormGroup = this.ideaFormService.createIdeaFormGroup();

  constructor(
    protected ideaService: IdeaService,
    protected ideaFormService: IdeaFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ idea }) => {
      this.idea = idea;
      if (idea) {
        this.updateForm(idea);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const idea = this.ideaFormService.getIdea(this.editForm);
    if (idea.id !== null) {
      this.subscribeToSaveResponse(this.ideaService.update(idea));
    } else {
      this.subscribeToSaveResponse(this.ideaService.create(idea));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIdea>>): void {
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

  protected updateForm(idea: IIdea): void {
    this.idea = idea;
    this.ideaFormService.resetForm(this.editForm, idea);
  }
}
