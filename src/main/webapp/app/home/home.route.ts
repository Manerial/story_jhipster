import { Route } from '@angular/router';
import { Authority } from 'app/shared/constants/authority.constants';

import { HomeComponent } from './home.component';
import { LibraryComponent } from './library/library.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

export const HOME_ROUTE: Route = {
  path: '',
  component: HomeComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title',
  },
};

export const LIBRARY_ROUTE: Route = {
  path: 'library',
  component: LibraryComponent,
  data: {
    authorities: [Authority.USER],
    pageTitle: 'library.title',
  },
  canActivate: [UserRouteAccessService],
};
