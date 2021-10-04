import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NidAuxHistoiresSharedModule } from 'app/shared/shared.module';
import { CoverComponent } from './cover.component';
import { CoverDetailComponent } from './cover-detail.component';
import { CoverUpdateComponent } from './cover-update.component';
import { CoverDeleteDialogComponent } from './cover-delete-dialog.component';
import { coverRoute } from './cover.route';

@NgModule({
  imports: [NidAuxHistoiresSharedModule, RouterModule.forChild(coverRoute)],
  declarations: [CoverComponent, CoverDetailComponent, CoverUpdateComponent, CoverDeleteDialogComponent],
  entryComponents: [CoverDeleteDialogComponent],
})
export class NidAuxHistoiresCoverModule {}
