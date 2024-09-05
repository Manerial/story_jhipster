import { ActivatedRouteSnapshot, Resolve, Route } from '@angular/router';

import { VisitComponent } from './visit.component';
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
    pageTitle: 'visit.main',
  },
};
