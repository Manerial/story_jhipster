import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIdea } from '../idea.model';
import { IdeaService } from '../service/idea.service';

export const ideaResolve = (route: ActivatedRouteSnapshot): Observable<null | IIdea> => {
  const id = route.params['id'];
  if (id) {
    return inject(IdeaService)
      .find(id)
      .pipe(
        mergeMap((idea: HttpResponse<IIdea>) => {
          if (idea.body) {
            return of(idea.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default ideaResolve;
