import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IScene } from '../scene.model';
import { SceneService } from '../service/scene.service';

export const sceneResolve = (route: ActivatedRouteSnapshot): Observable<null | IScene> => {
  const id = route.params['id'];
  if (id) {
    return inject(SceneService)
      .find(id)
      .pipe(
        mergeMap((scene: HttpResponse<IScene>) => {
          if (scene.body) {
            return of(scene.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default sceneResolve;
