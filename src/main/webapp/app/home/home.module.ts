import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NidAuxHistoiresSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE, LIBRARY_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { LibraryComponent } from './library/library.component';

@NgModule({
  imports: [NidAuxHistoiresSharedModule, RouterModule.forChild([HOME_ROUTE, LIBRARY_ROUTE])],
  declarations: [HomeComponent, LibraryComponent],
})
export class NidAuxHistoiresHomeModule {}
