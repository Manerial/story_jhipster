import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBookStatus } from '../book-status.model';
import { BookStatusService } from '../service/book-status.service';

export const bookStatusResolve = (route: ActivatedRouteSnapshot): Observable<null | IBookStatus> => {
  const id = route.params['id'];
  if (id) {
    return inject(BookStatusService)
      .find(id)
      .pipe(
        mergeMap((bookStatus: HttpResponse<IBookStatus>) => {
          if (bookStatus.body) {
            return of(bookStatus.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default bookStatusResolve;
