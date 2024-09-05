import { Route } from '@angular/router';
import { Authority } from 'app/shared/constants/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

import { LibraryComponent } from './library.component';
import { ReaderComponent } from './reader/reader.component';

export const LIBRARY_ROUTE: Route = {
  path: 'library',
  component: LibraryComponent,
  data: {
    authorities: [Authority.USER],
    pageTitle: 'library.title',
  },
  canActivate: [UserRouteAccessService],
};

export const READER_ROUTE: Route = {
  path: 'reader/book/:bookId',
  component: ReaderComponent,
  data: {
    authorities: [Authority.USER],
  },
  canActivate: [UserRouteAccessService],
};
