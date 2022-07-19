import { Routes } from '@angular/router';

import { AuthorsComponent } from './authors.component';
import { visitRoute } from './visit/visit.route';

export const authorsState: Routes = [
  {
    path: '',
    component: AuthorsComponent,
    data: {
      pageTitle: 'global.menu.authors',
    },
  },
  visitRoute,
];
