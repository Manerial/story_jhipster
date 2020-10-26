import { Route } from '@angular/router';
import { Authority } from 'app/shared/constants/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IdeaGeneratorComponent } from './idea-generator/idea-generator.component';
import { WordGeneratorComponent } from './word-generator/word-generator.component';

export const IDEA_ROUTE: Route = {
  path: 'generator/idea',
  component: IdeaGeneratorComponent,
  data: {
    authorities: [Authority.USER],
    pageTitle: 'generator.idea.title',
  },
  canActivate: [UserRouteAccessService],
};

export const WORD_ROUTE: Route = {
  path: 'generator/word',
  component: WordGeneratorComponent,
  data: {
    authorities: [Authority.USER],
    pageTitle: 'generator.word.title',
  },
  canActivate: [UserRouteAccessService],
};
