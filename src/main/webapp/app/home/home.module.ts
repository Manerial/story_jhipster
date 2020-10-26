import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NidAuxHistoiresSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE, MENTIONS_ROUTE, HELP_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { HelpComponent } from './help/help.component';
import { InformationsComponent } from './informations/informations.component';

@NgModule({
  imports: [NidAuxHistoiresSharedModule, RouterModule.forChild([HOME_ROUTE, MENTIONS_ROUTE, HELP_ROUTE])],
  declarations: [HomeComponent, HelpComponent, InformationsComponent],
})
export class NidAuxHistoiresHomeModule {}
