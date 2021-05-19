import { ActivatedRouteSnapshot, Resolve, Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { VisitComponent } from './visit.component';
import { Authority } from 'app/shared/constants/authority.constants';
import { Injectable } from '@angular/core';
import { IUser, User } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { Observable, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserManagementResolve implements Resolve<IUser> {
  constructor(private service: UserService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUser> {
    const id = route.params['login'];
    if (id) {
      return this.service.findLight(id);
    }
    return of(new User());
  }
}

export const visitRoute: Route = {
  path: ':login/visit',
  component: VisitComponent,
  resolve: {
    user: UserManagementResolve,
  },
  data: {
    authorities: [Authority.USER],
    pageTitle: 'global.menu.account.visit',
  },
  canActivate: [UserRouteAccessService],
};
