import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NidAuxHistoiresSharedModule } from 'app/shared/shared.module';
import { IDEA_ROUTE, WORD_ROUTE } from './generator.route';
import { IdeaGeneratorComponent } from './idea-generator/idea-generator.component';
import { WordGeneratorComponent } from './word-generator/word-generator.component';

@NgModule({
  imports: [NidAuxHistoiresSharedModule, RouterModule.forChild([IDEA_ROUTE, WORD_ROUTE])],
  declarations: [IdeaGeneratorComponent, WordGeneratorComponent],
})
export class NidAuxHistoiresGeneratorModule {}
