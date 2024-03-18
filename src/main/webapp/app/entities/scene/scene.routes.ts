import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SceneComponent } from './list/scene.component';
import { SceneDetailComponent } from './detail/scene-detail.component';
import { SceneUpdateComponent } from './update/scene-update.component';
import SceneResolve from './route/scene-routing-resolve.service';

const sceneRoute: Routes = [
  {
    path: '',
    component: SceneComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SceneDetailComponent,
    resolve: {
      scene: SceneResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SceneUpdateComponent,
    resolve: {
      scene: SceneResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SceneUpdateComponent,
    resolve: {
      scene: SceneResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default sceneRoute;
