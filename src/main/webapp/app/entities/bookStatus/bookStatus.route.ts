import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBookStatus, BookStatus } from 'app/shared/model/bookStatus.model';
import { BookStatusService } from './bookStatus.service';
import { BookStatusComponent } from './bookStatus.component';
import { BookStatusDetailComponent } from './bookStatus-detail.component';
import { BookStatusUpdateComponent } from './bookStatus-update.component';

@Injectable({ providedIn: 'root' })
export class BookStatusResolve implements Resolve<IBookStatus> {
  constructor(private service: BookStatusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBookStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bookStatus: HttpResponse<BookStatus>) => {
          if (bookStatus.body) {
            return of(bookStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BookStatus());
  }
}

export const bookStatusRoute: Routes = [
  {
    path: '',
    component: BookStatusComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nidAuxHistoiresApp.bookStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BookStatusDetailComponent,
    resolve: {
      bookStatus: BookStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nidAuxHistoiresApp.bookStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BookStatusUpdateComponent,
    resolve: {
      bookStatus: BookStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nidAuxHistoiresApp.bookStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BookStatusUpdateComponent,
    resolve: {
      bookStatus: BookStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nidAuxHistoiresApp.bookStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
