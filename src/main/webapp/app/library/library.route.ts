import { Route } from '@angular/router';
import { Authority } from 'app/shared/constants/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

import { LibraryComponent } from './library.component';

export const LIBRARY_ROUTE: Route = {
  path: 'library',
  component: LibraryComponent,
  data: {
    authorities: [Authority.USER],
    pageTitle: 'library.title',
  },
  canActivate: [UserRouteAccessService],
};
