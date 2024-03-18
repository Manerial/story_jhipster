import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NidAuxHistoiresSharedModule } from 'app/shared/shared.module';
import { PartComponent } from './part.component';
import { PartDetailComponent } from './part-detail.component';
import { PartUpdateComponent } from './part-update.component';
import { PartDeleteDialogComponent } from './part-delete-dialog.component';
import { partRoute } from './part.route';

@NgModule({
  imports: [NidAuxHistoiresSharedModule, RouterModule.forChild(partRoute)],
  declarations: [PartComponent, PartDetailComponent, PartUpdateComponent, PartDeleteDialogComponent],
  entryComponents: [PartDeleteDialogComponent],
})
export class NidAuxHistoiresPartModule {}
