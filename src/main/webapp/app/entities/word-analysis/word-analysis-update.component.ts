import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWordAnalysis, WordAnalysis } from 'app/shared/model/word-analysis.model';
import { WordAnalysisService } from './word-analysis.service';

@Component({
  selector: 'jhi-word-analysis-update',
  templateUrl: './word-analysis-update.component.html',
})
export class WordAnalysisUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    type: [],
    name: [],
    analysis: [],
  });

  constructor(protected wordAnalysisService: WordAnalysisService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wordAnalysis }) => {
      this.updateForm(wordAnalysis);
    });
  }

  updateForm(wordAnalysis: IWordAnalysis): void {
    this.editForm.patchValue({
      id: wordAnalysis.id,
      type: wordAnalysis.type,
      name: wordAnalysis.name,
      analysis: wordAnalysis.analysis,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const wordAnalysis = this.createFromForm();
    if (wordAnalysis.id !== undefined) {
      this.subscribeToSaveResponse(this.wordAnalysisService.update(wordAnalysis));
    } else {
      this.subscribeToSaveResponse(this.wordAnalysisService.create(wordAnalysis));
    }
  }

  private createFromForm(): IWordAnalysis {
    return {
      ...new WordAnalysis(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      name: this.editForm.get(['name'])!.value,
      analysis: this.editForm.get(['analysis'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWordAnalysis>>): void {
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
