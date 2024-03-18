import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IWordAnalysis } from '../word-analysis.model';
import { WordAnalysisService } from '../service/word-analysis.service';
import { WordAnalysisFormService, WordAnalysisFormGroup } from './word-analysis-form.service';

@Component({
  standalone: true,
  selector: 'jhi-word-analysis-update',
  templateUrl: './word-analysis-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class WordAnalysisUpdateComponent implements OnInit {
  isSaving = false;
  wordAnalysis: IWordAnalysis | null = null;

  editForm: WordAnalysisFormGroup = this.wordAnalysisFormService.createWordAnalysisFormGroup();

  constructor(
    protected wordAnalysisService: WordAnalysisService,
    protected wordAnalysisFormService: WordAnalysisFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wordAnalysis }) => {
      this.wordAnalysis = wordAnalysis;
      if (wordAnalysis) {
        this.updateForm(wordAnalysis);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const wordAnalysis = this.wordAnalysisFormService.getWordAnalysis(this.editForm);
    if (wordAnalysis.id !== null) {
      this.subscribeToSaveResponse(this.wordAnalysisService.update(wordAnalysis));
    } else {
      this.subscribeToSaveResponse(this.wordAnalysisService.create(wordAnalysis));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWordAnalysis>>): void {
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

  protected updateForm(wordAnalysis: IWordAnalysis): void {
    this.wordAnalysis = wordAnalysis;
    this.wordAnalysisFormService.resetForm(this.editForm, wordAnalysis);
  }
}
