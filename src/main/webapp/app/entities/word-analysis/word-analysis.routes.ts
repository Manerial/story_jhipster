import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { WordAnalysisComponent } from './list/word-analysis.component';
import { WordAnalysisDetailComponent } from './detail/word-analysis-detail.component';
import { WordAnalysisUpdateComponent } from './update/word-analysis-update.component';
import WordAnalysisResolve from './route/word-analysis-routing-resolve.service';

const wordAnalysisRoute: Routes = [
  {
    path: '',
    component: WordAnalysisComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WordAnalysisDetailComponent,
    resolve: {
      wordAnalysis: WordAnalysisResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WordAnalysisUpdateComponent,
    resolve: {
      wordAnalysis: WordAnalysisResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WordAnalysisUpdateComponent,
    resolve: {
      wordAnalysis: WordAnalysisResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default wordAnalysisRoute;
