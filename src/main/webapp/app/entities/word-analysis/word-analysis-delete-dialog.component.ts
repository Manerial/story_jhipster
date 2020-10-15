import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWordAnalysis } from 'app/shared/model/word-analysis.model';
import { WordAnalysisService } from './word-analysis.service';

@Component({
  templateUrl: './word-analysis-delete-dialog.component.html',
})
export class WordAnalysisDeleteDialogComponent {
  wordAnalysis?: IWordAnalysis;

  constructor(
    protected wordAnalysisService: WordAnalysisService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.wordAnalysisService.delete(id).subscribe(() => {
      this.eventManager.broadcast('wordAnalysisListModification');
      this.activeModal.close();
    });
  }
}
