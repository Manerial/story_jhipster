import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'book',
    data: { pageTitle: 'nidAuxHistoiresApp.book.home.title' },
    loadChildren: () => import('./book/book.routes'),
  },
  {
    path: 'part',
    data: { pageTitle: 'nidAuxHistoiresApp.part.home.title' },
    loadChildren: () => import('./part/part.routes'),
  },
  {
    path: 'chapter',
    data: { pageTitle: 'nidAuxHistoiresApp.chapter.home.title' },
    loadChildren: () => import('./chapter/chapter.routes'),
  },
  {
    path: 'scene',
    data: { pageTitle: 'nidAuxHistoiresApp.scene.home.title' },
    loadChildren: () => import('./scene/scene.routes'),
  },
  {
    path: 'image',
    data: { pageTitle: 'nidAuxHistoiresApp.image.home.title' },
    loadChildren: () => import('./image/image.routes'),
  },
  {
    path: 'idea',
    data: { pageTitle: 'nidAuxHistoiresApp.idea.home.title' },
    loadChildren: () => import('./idea/idea.routes'),
  },
  {
    path: 'word-analysis',
    data: { pageTitle: 'nidAuxHistoiresApp.wordAnalysis.home.title' },
    loadChildren: () => import('./word-analysis/word-analysis.routes'),
  },
  {
    path: 'comment',
    data: { pageTitle: 'nidAuxHistoiresApp.comment.home.title' },
    loadChildren: () => import('./comment/comment.routes'),
  },
  {
    path: 'bonus',
    data: { pageTitle: 'nidAuxHistoiresApp.bonus.home.title' },
    loadChildren: () => import('./bonus/bonus.routes'),
  },
  {
    path: 'book-status',
    data: { pageTitle: 'nidAuxHistoiresApp.bookStatus.home.title' },
    loadChildren: () => import('./book-status/book-status.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
