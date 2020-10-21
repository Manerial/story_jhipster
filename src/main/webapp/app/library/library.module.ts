import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NidAuxHistoiresSharedModule } from 'app/shared/shared.module';
import { LIBRARY_ROUTE } from './library.route';
import { LibraryComponent } from './library.component';

@NgModule({
  imports: [NidAuxHistoiresSharedModule, RouterModule.forChild([LIBRARY_ROUTE])],
  declarations: [LibraryComponent],
})
export class NidAuxHistoiresLibraryModule {}
