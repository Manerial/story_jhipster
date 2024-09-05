import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NidAuxHistoiresSharedModule } from 'app/shared/shared.module';
import { ChapterComponent } from './chapter.component';
import { ChapterDetailComponent } from './chapter-detail.component';
import { ChapterUpdateComponent } from './chapter-update.component';
import { ChapterDeleteDialogComponent } from './chapter-delete-dialog.component';
import { chapterRoute } from './chapter.route';

@NgModule({
  imports: [NidAuxHistoiresSharedModule, RouterModule.forChild(chapterRoute)],
  declarations: [ChapterComponent, ChapterDetailComponent, ChapterUpdateComponent, ChapterDeleteDialogComponent],
  entryComponents: [ChapterDeleteDialogComponent],
})
export class NidAuxHistoiresChapterModule {}
