import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { IdeaComponent } from './list/idea.component';
import { IdeaDetailComponent } from './detail/idea-detail.component';
import { IdeaUpdateComponent } from './update/idea-update.component';
import IdeaResolve from './route/idea-routing-resolve.service';

const ideaRoute: Routes = [
  {
    path: '',
    component: IdeaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IdeaDetailComponent,
    resolve: {
      idea: IdeaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IdeaUpdateComponent,
    resolve: {
      idea: IdeaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IdeaUpdateComponent,
    resolve: {
      idea: IdeaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default ideaRoute;
