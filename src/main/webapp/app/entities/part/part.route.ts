import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPart, Part } from 'app/shared/model/part.model';
import { PartService } from './part.service';
import { PartComponent } from './part.component';
import { PartDetailComponent } from './part-detail.component';
import { PartUpdateComponent } from './part-update.component';

@Injectable({ providedIn: 'root' })
export class PartResolve implements Resolve<IPart> {
  constructor(private service: PartService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPart> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((part: HttpResponse<Part>) => {
          if (part.body) {
            return of(part.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Part());
  }
}

export const partRoute: Routes = [
  {
    path: '',
    component: PartComponent,
    data: {
      authorities: [Authority.ADMIN],
      defaultSort: 'id,asc',
      pageTitle: 'nidAuxHistoiresApp.part.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartDetailComponent,
    resolve: {
      part: PartResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'nidAuxHistoiresApp.part.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartUpdateComponent,
    resolve: {
      part: PartResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'nidAuxHistoiresApp.part.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartUpdateComponent,
    resolve: {
      part: PartResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'nidAuxHistoiresApp.part.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
