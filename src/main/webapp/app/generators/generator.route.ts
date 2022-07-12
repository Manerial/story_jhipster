import { Route } from '@angular/router';

import { IdeaGeneratorComponent } from './idea-generator/idea-generator.component';
import { WordGeneratorComponent } from './word-generator/word-generator.component';

export const IDEA_ROUTE: Route = {
  path: 'generator/idea',
  component: IdeaGeneratorComponent,
  data: {
    pageTitle: 'generator.idea.title',
  },
};

export const WORD_ROUTE: Route = {
  path: 'generator/word',
  component: WordGeneratorComponent,
  data: {
    pageTitle: 'generator.word.title',
  },
};
