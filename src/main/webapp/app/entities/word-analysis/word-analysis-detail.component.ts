import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWordAnalysis } from 'app/shared/model/word-analysis.model';

@Component({
  selector: 'jhi-word-analysis-detail',
  templateUrl: './word-analysis-detail.component.html',
})
export class WordAnalysisDetailComponent implements OnInit {
  wordAnalysis: IWordAnalysis | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wordAnalysis }) => (this.wordAnalysis = wordAnalysis));
  }

  previousState(): void {
    window.history.back();
  }
}
