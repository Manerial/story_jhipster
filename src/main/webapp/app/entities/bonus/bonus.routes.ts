import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { BonusComponent } from './list/bonus.component';
import { BonusDetailComponent } from './detail/bonus-detail.component';
import { BonusUpdateComponent } from './update/bonus-update.component';
import BonusResolve from './route/bonus-routing-resolve.service';

const bonusRoute: Routes = [
  {
    path: '',
    component: BonusComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BonusDetailComponent,
    resolve: {
      bonus: BonusResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BonusUpdateComponent,
    resolve: {
      bonus: BonusResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BonusUpdateComponent,
    resolve: {
      bonus: BonusResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default bonusRoute;
