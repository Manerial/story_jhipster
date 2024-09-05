import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'book',
        loadChildren: () => import('./book/book.module').then(m => m.NidAuxHistoiresBookModule),
      },
      {
        path: 'part',
        loadChildren: () => import('./part/part.module').then(m => m.NidAuxHistoiresPartModule),
      },
      {
        path: 'chapter',
        loadChildren: () => import('./chapter/chapter.module').then(m => m.NidAuxHistoiresChapterModule),
      },
      {
        path: 'scene',
        loadChildren: () => import('./scene/scene.module').then(m => m.NidAuxHistoiresSceneModule),
      },
      {
        path: 'image',
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
        path: 'comment',
        loadChildren: () => import('./comment/comment.module').then(m => m.NidAuxHistoiresCommentModule),
      },
      {
        path: 'bonus',
        loadChildren: () => import('./bonus/bonus.module').then(m => m.NidAuxHistoiresBonusModule),
      },
      {
        path: 'library',
        loadChildren: () => import('./library/library.module').then(m => m.NidAuxHistoiresLibraryModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class NidAuxHistoiresEntityModule {}
