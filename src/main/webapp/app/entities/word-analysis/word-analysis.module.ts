import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NidAuxHistoiresSharedModule } from 'app/shared/shared.module';
import { WordAnalysisComponent } from './word-analysis.component';
import { WordAnalysisDetailComponent } from './word-analysis-detail.component';
import { WordAnalysisUpdateComponent } from './word-analysis-update.component';
import { WordAnalysisDeleteDialogComponent } from './word-analysis-delete-dialog.component';
import { wordAnalysisRoute } from './word-analysis.route';

@NgModule({
  imports: [NidAuxHistoiresSharedModule, RouterModule.forChild(wordAnalysisRoute)],
  declarations: [WordAnalysisComponent, WordAnalysisDetailComponent, WordAnalysisUpdateComponent, WordAnalysisDeleteDialogComponent],
  entryComponents: [WordAnalysisDeleteDialogComponent],
})
export class NidAuxHistoiresWordAnalysisModule {}
