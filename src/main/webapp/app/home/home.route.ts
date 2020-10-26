import { Route } from '@angular/router';

import { HomeComponent } from './home.component';
import { HelpComponent } from './help/help.component';
import { InformationsComponent } from './informations/informations.component';

export const HOME_ROUTE: Route = {
  path: '',
  component: HomeComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title',
  },
};

export const MENTIONS_ROUTE: Route = {
  path: 'mentions',
  component: InformationsComponent,
  data: {
    authorities: [],
    pageTitle: 'mentions.title',
  },
};

export const HELP_ROUTE: Route = {
  path: 'help',
  component: HelpComponent,
  data: {
    authorities: [],
    pageTitle: 'help.title',
  },
};
