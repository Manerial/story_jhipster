import { Route } from '@angular/router';
import { Authority } from 'app/shared/constants/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

import { LibraryComponent } from './library.component';
import { ReaderComponent } from './reader/reader.component';
import { CommentsComponent } from './comments/comments.component';
import { BonusListComponent } from './bonuses/bonus-list/bonus-list.component';

export const LIBRARY_ROUTE: Route = {
  path: 'library',
  component: LibraryComponent,
  data: {
    pageTitle: 'library.title',
  },
};

export const FAVORITS_ROUTE: Route = {
  path: 'favorits',
  component: LibraryComponent,
  data: {
    pageTitle: 'library.favorits.title',
    authorities: [Authority.USER],
  },
  canActivate: [UserRouteAccessService],
};

export const READER_ROUTE: Route = {
  path: 'reader/book/:bookId',
  component: ReaderComponent,
  data: {
    pageTitle: 'library.reader.title',
  },
};

export const BONUSES_ROUTE: Route = {
  path: 'bonus/book/:bookId',
  component: BonusListComponent,
  data: {
    pageTitle: 'library.bonuses.title',
  },
};

export const COMMENTS_ROUTE: Route = {
  path: 'comments/book/:bookId',
  component: CommentsComponent,
  data: {
    pageTitle: 'library.comments.title',
  },
};
