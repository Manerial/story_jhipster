import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NidAuxHistoiresSharedModule } from 'app/shared/shared.module';
import { BookStatusComponent } from './bookStatus.component';
import { BookStatusDetailComponent } from './bookStatus-detail.component';
import { BookStatusUpdateComponent } from './bookStatus-update.component';
import { BookStatusDeleteDialogComponent } from './bookStatus-delete-dialog.component';
import { bookStatusRoute } from './bookStatus.route';

@NgModule({
  imports: [NidAuxHistoiresSharedModule, RouterModule.forChild(bookStatusRoute)],
  declarations: [BookStatusComponent, BookStatusDetailComponent, BookStatusUpdateComponent, BookStatusDeleteDialogComponent],
  entryComponents: [BookStatusDeleteDialogComponent],
})
export class NidAuxHistoiresBookStatusModule {}
