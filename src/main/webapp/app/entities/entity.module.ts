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
          authorities: [Authority.ADMIN],
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./book/book.module').then(m => m.NidAuxHistoiresBookModule),
      },
      {
        path: 'entities/part',
        data: {
          authorities: [Authority.ADMIN],
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./part/part.module').then(m => m.NidAuxHistoiresPartModule),
      },
      {
        path: 'entities/chapter',
        data: {
          authorities: [Authority.ADMIN],
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./chapter/chapter.module').then(m => m.NidAuxHistoiresChapterModule),
      },
      {
        path: 'entities/scene',
        data: {
          authorities: [Authority.ADMIN],
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./scene/scene.module').then(m => m.NidAuxHistoiresSceneModule),
      },
      {
        path: 'entities/image',
        data: {
          authorities: [Authority.ADMIN],
        },
        canActivate: [UserRouteAccessService],
        loadChildren: () => import('./image/image.module').then(m => m.NidAuxHistoiresImageModule),
      },
      {
        path: 'idea',
        loadChildren: () => import('./idea/idea.module').then(m => m.NidAuxHistoiresIdeaModule),
      },
      {
        path: 'word-analysis',
        loadChildren: () => import('./word-analysis/word-analysis.module').then(m => m.NidAuxHistoiresWordAnalysisModule),
      },
      {
        path: 'scene',
        loadChildren: () => import('./scene/scene.module').then(m => m.NidAuxHistoiresSceneModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class NidAuxHistoiresEntityModule {}
