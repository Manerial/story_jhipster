import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWordAnalysis, WordAnalysis } from 'app/shared/model/word-analysis.model';
import { WordAnalysisService } from './word-analysis.service';
import { WordAnalysisComponent } from './word-analysis.component';
import { WordAnalysisDetailComponent } from './word-analysis-detail.component';
import { WordAnalysisUpdateComponent } from './word-analysis-update.component';

@Injectable({ providedIn: 'root' })
export class WordAnalysisResolve implements Resolve<IWordAnalysis> {
  constructor(private service: WordAnalysisService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWordAnalysis> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((wordAnalysis: HttpResponse<WordAnalysis>) => {
          if (wordAnalysis.body) {
            return of(wordAnalysis.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WordAnalysis());
  }
}

export const wordAnalysisRoute: Routes = [
  {
    path: '',
    component: WordAnalysisComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'nidAuxHistoiresApp.wordAnalysis.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WordAnalysisDetailComponent,
    resolve: {
      wordAnalysis: WordAnalysisResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nidAuxHistoiresApp.wordAnalysis.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WordAnalysisUpdateComponent,
    resolve: {
      wordAnalysis: WordAnalysisResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nidAuxHistoiresApp.wordAnalysis.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WordAnalysisUpdateComponent,
    resolve: {
      wordAnalysis: WordAnalysisResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'nidAuxHistoiresApp.wordAnalysis.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
