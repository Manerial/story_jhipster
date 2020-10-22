import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NidAuxHistoiresSharedModule } from 'app/shared/shared.module';
import { LIBRARY_ROUTE, READER_ROUTE } from './library.route';
import { LibraryComponent } from './library.component';
import { ReaderComponent } from './reader/reader.component';
import { TextComponent } from './reader/text/text.component';
import { TitleComponent } from './reader/title/title.component';
import { SidebarComponent } from '../layouts/sidebar/sidebar.component';

@NgModule({
  imports: [NidAuxHistoiresSharedModule, RouterModule.forChild([LIBRARY_ROUTE, READER_ROUTE])],
  declarations: [LibraryComponent, ReaderComponent, TextComponent, TitleComponent, SidebarComponent],
})
export class NidAuxHistoiresLibraryModule {}
