import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NidAuxHistoiresSharedModule } from 'app/shared/shared.module';
import { BonusComponent } from './bonus.component';
import { BonusDetailComponent } from './bonus-detail.component';
import { BonusUpdateComponent } from './bonus-update.component';
import { BonusDeleteDialogComponent } from './bonus-delete-dialog.component';
import { bonusRoute } from './bonus.route';

@NgModule({
  imports: [NidAuxHistoiresSharedModule, RouterModule.forChild(bonusRoute)],
  declarations: [BonusComponent, BonusDetailComponent, BonusUpdateComponent, BonusDeleteDialogComponent],
  entryComponents: [BonusDeleteDialogComponent],
})
export class NidAuxHistoiresBonusModule {}
