import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { BookStatusComponent } from './list/book-status.component';
import { BookStatusDetailComponent } from './detail/book-status-detail.component';
import { BookStatusUpdateComponent } from './update/book-status-update.component';
import BookStatusResolve from './route/book-status-routing-resolve.service';

const bookStatusRoute: Routes = [
  {
    path: '',
    component: BookStatusComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BookStatusDetailComponent,
    resolve: {
      bookStatus: BookStatusResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BookStatusUpdateComponent,
    resolve: {
      bookStatus: BookStatusResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BookStatusUpdateComponent,
    resolve: {
      bookStatus: BookStatusResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default bookStatusRoute;
