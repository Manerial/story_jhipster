import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IChapter, Chapter } from 'app/shared/model/chapter.model';
import { ChapterService } from './chapter.service';
import { ChapterComponent } from './chapter.component';
import { ChapterDetailComponent } from './chapter-detail.component';
import { ChapterUpdateComponent } from './chapter-update.component';

@Injectable({ providedIn: 'root' })
export class ChapterResolve implements Resolve<IChapter> {
  constructor(private service: ChapterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChapter> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((chapter: HttpResponse<Chapter>) => {
          if (chapter.body) {
            return of(chapter.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Chapter());
  }
}

export const chapterRoute: Routes = [
  {
    path: '',
    component: ChapterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nidAuxHistoiresApp.chapter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChapterDetailComponent,
    resolve: {
      chapter: ChapterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nidAuxHistoiresApp.chapter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChapterUpdateComponent,
    resolve: {
      chapter: ChapterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nidAuxHistoiresApp.chapter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChapterUpdateComponent,
    resolve: {
      chapter: ChapterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nidAuxHistoiresApp.chapter.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
