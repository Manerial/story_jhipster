import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICover, Cover } from 'app/shared/model/cover.model';
import { CoverService } from './cover.service';
import { CoverComponent } from './cover.component';
import { CoverDetailComponent } from './cover-detail.component';
import { CoverUpdateComponent } from './cover-update.component';

@Injectable({ providedIn: 'root' })
export class CoverResolve implements Resolve<ICover> {
  constructor(private service: CoverService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICover> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cover: HttpResponse<Cover>) => {
          if (cover.body) {
            return of(cover.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Cover());
  }
}

export const coverRoute: Routes = [
  {
    path: '',
    component: CoverComponent,
    data: {
      authorities: [Authority.ADMIN, Authority.AUTHOR],
      defaultSort: 'id,asc',
      pageTitle: 'nidAuxHistoiresApp.cover.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CoverDetailComponent,
    resolve: {
      cover: CoverResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.AUTHOR],
      pageTitle: 'nidAuxHistoiresApp.cover.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CoverUpdateComponent,
    resolve: {
      cover: CoverResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.AUTHOR],
      pageTitle: 'nidAuxHistoiresApp.cover.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CoverUpdateComponent,
    resolve: {
      cover: CoverResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.AUTHOR],
      pageTitle: 'nidAuxHistoiresApp.cover.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
