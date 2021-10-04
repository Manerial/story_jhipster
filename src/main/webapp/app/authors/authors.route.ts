import { Routes } from '@angular/router';

import { AuthorsComponent } from './authors.component';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { visitRoute } from './visit/visit.route';

export const authorsState: Routes = [
  {
    path: '',
    component: AuthorsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'global.menu.authors',
    },
    canActivate: [UserRouteAccessService],
  },
  visitRoute,
];
