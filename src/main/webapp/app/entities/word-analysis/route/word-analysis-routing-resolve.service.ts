import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IWordAnalysis } from '../word-analysis.model';
import { WordAnalysisService } from '../service/word-analysis.service';

export const wordAnalysisResolve = (route: ActivatedRouteSnapshot): Observable<null | IWordAnalysis> => {
  const id = route.params['id'];
  if (id) {
    return inject(WordAnalysisService)
      .find(id)
      .pipe(
        mergeMap((wordAnalysis: HttpResponse<IWordAnalysis>) => {
          if (wordAnalysis.body) {
            return of(wordAnalysis.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default wordAnalysisResolve;
