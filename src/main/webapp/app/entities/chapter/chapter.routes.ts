import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ChapterComponent } from './list/chapter.component';
import { ChapterDetailComponent } from './detail/chapter-detail.component';
import { ChapterUpdateComponent } from './update/chapter-update.component';
import ChapterResolve from './route/chapter-routing-resolve.service';

const chapterRoute: Routes = [
  {
    path: '',
    component: ChapterComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChapterDetailComponent,
    resolve: {
      chapter: ChapterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChapterUpdateComponent,
    resolve: {
      chapter: ChapterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChapterUpdateComponent,
    resolve: {
      chapter: ChapterResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default chapterRoute;
