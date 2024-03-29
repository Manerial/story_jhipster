import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Authority } from 'app/shared/constants/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'entities/book',
        data: {
          authorities: [Authority.AUTHOR, Authority.ADMIN],
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./book/book.module').then(m => m.NidAuxHistoiresBookModule),
      },
      {
        path: 'entities/part',
        data: {
          authorities: [Authority.AUTHOR, Authority.ADMIN],
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./part/part.module').then(m => m.NidAuxHistoiresPartModule),
      },
      {
        path: 'entities/chapter',
        data: {
          authorities: [Authority.AUTHOR, Authority.ADMIN],
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./chapter/chapter.module').then(m => m.NidAuxHistoiresChapterModule),
      },
      {
        path: 'entities/scene',
        data: {
          authorities: [Authority.AUTHOR, Authority.ADMIN],
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./scene/scene.module').then(m => m.NidAuxHistoiresSceneModule),
      },
      {
        path: 'entities/cover',
        data: {
          authorities: [Authority.AUTHOR, Authority.ADMIN],
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./cover/cover.module').then(m => m.NidAuxHistoiresCoverModule),
      },
      {
        path: 'entities/bonus',
        data: {
          authorities: [Authority.AUTHOR, Authority.ADMIN],
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./bonus/bonus.module').then(m => m.NidAuxHistoiresBonusModule),
      },
      {
        path: 'entities/idea',
        data: {
          authorities: [Authority.ADMIN],
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./idea/idea.module').then(m => m.NidAuxHistoiresIdeaModule),
      },
      {
        path: 'entities/word-analysis',
        data: {
          authorities: [Authority.ADMIN],
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./word-analysis/word-analysis.module').then(m => m.NidAuxHistoiresWordAnalysisModule),
      },
      {
        path: 'entities/comment',
        data: {
          authorities: [Authority.ADMIN],
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./comment/comment.module').then(m => m.NidAuxHistoiresCommentModule),
      },
      {
        path: 'entities/bookStatus',
        data: {
          authorities: [Authority.ADMIN],
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./bookStatus/bookStatus.module').then(m => m.NidAuxHistoiresBookStatusModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class NidAuxHistoiresEntityModule {}
