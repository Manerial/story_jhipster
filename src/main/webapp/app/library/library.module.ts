import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NidAuxHistoiresSharedModule } from 'app/shared/shared.module';
import { LIBRARY_ROUTE, READER_ROUTE, BONUSES_ROUTE, IMAGE_VIEW_ROUTE, COMMENTS_ROUTE } from './library.route';
import { LibraryComponent } from './library.component';
import { ReaderComponent } from './reader/reader.component';
import { TextComponent } from './reader/text/text.component';
import { TitleComponent } from './reader/title/title.component';
import { SidebarComponent } from '../layouts/sidebar/sidebar.component';
import { ImageViewerComponent } from './bonuses/image-viewer/image-viewer.component';
import { BonusListComponent } from './bonuses/bonus-list/bonus-list.component';
import { DownloadBookDialogComponent } from './download-book-dialog.component';
import { CommentsComponent } from './comments/comments.component';
import { AddCommentDialogComponent } from './comments/add-comment.component';

@NgModule({
  imports: [
    NidAuxHistoiresSharedModule,
    RouterModule.forChild([LIBRARY_ROUTE, READER_ROUTE, BONUSES_ROUTE, COMMENTS_ROUTE, IMAGE_VIEW_ROUTE]),
  ],
  declarations: [
    LibraryComponent,
    ReaderComponent,
    CommentsComponent,
    TextComponent,
    TitleComponent,
    SidebarComponent,
    BonusListComponent,
    ImageViewerComponent,
    DownloadBookDialogComponent,
    AddCommentDialogComponent,
  ],
  entryComponents: [DownloadBookDialogComponent, AddCommentDialogComponent],
})
export class NidAuxHistoiresLibraryModule {}
