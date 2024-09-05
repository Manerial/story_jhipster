import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IScene, Scene } from 'app/shared/model/scene.model';
import { SceneService } from './scene.service';
import { SceneComponent } from './scene.component';
import { SceneDetailComponent } from './scene-detail.component';
import { SceneUpdateComponent } from './scene-update.component';

@Injectable({ providedIn: 'root' })
export class SceneResolve implements Resolve<IScene> {
  constructor(private service: SceneService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IScene> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((scene: HttpResponse<Scene>) => {
          if (scene.body) {
            return of(scene.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Scene());
  }
}

export const sceneRoute: Routes = [
  {
    path: '',
    component: SceneComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nidAuxHistoiresApp.scene.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SceneDetailComponent,
    resolve: {
      scene: SceneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nidAuxHistoiresApp.scene.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SceneUpdateComponent,
    resolve: {
      scene: SceneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nidAuxHistoiresApp.scene.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SceneUpdateComponent,
    resolve: {
      scene: SceneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nidAuxHistoiresApp.scene.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
