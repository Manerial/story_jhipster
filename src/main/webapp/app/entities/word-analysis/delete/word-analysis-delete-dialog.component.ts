import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IWordAnalysis } from '../word-analysis.model';
import { WordAnalysisService } from '../service/word-analysis.service';

@Component({
  standalone: true,
  templateUrl: './word-analysis-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class WordAnalysisDeleteDialogComponent {
  wordAnalysis?: IWordAnalysis;

  constructor(
    protected wordAnalysisService: WordAnalysisService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.wordAnalysisService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
