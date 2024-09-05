import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NidAuxHistoiresSharedModule } from 'app/shared/shared.module';

import { AuthorsComponent } from './authors.component';
import { authorsState } from './authors.route';
import { VisitComponent } from './visit/visit.component';

@NgModule({
  imports: [NidAuxHistoiresSharedModule, RouterModule.forChild(authorsState)],
  declarations: [AuthorsComponent, VisitComponent],
})
export class AuthorsModule {}
