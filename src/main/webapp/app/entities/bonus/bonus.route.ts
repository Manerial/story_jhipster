import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBonus, Bonus } from 'app/shared/model/bonus.model';
import { BonusService } from './bonus.service';
import { BonusComponent } from './bonus.component';
import { BonusDetailComponent } from './bonus-detail.component';
import { BonusUpdateComponent } from './bonus-update.component';

@Injectable({ providedIn: 'root' })
export class BonusResolve implements Resolve<IBonus> {
  constructor(private service: BonusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBonus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bonus: HttpResponse<Bonus>) => {
          if (bonus.body) {
            return of(bonus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Bonus());
  }
}

export const bonusRoute: Routes = [
  {
    path: '',
    component: BonusComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nidAuxHistoiresApp.bonus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BonusDetailComponent,
    resolve: {
      bonus: BonusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nidAuxHistoiresApp.bonus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BonusUpdateComponent,
    resolve: {
      bonus: BonusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nidAuxHistoiresApp.bonus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BonusUpdateComponent,
    resolve: {
      bonus: BonusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nidAuxHistoiresApp.bonus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
