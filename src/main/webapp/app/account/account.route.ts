import { Routes } from '@angular/router';

import { activateRoute } from './activate/activate.route';
import { passwordRoute } from './password/password.route';
import { passwordResetFinishRoute } from './password-reset/finish/password-reset-finish.route';
import { passwordResetInitRoute } from './password-reset/init/password-reset-init.route';
import { registerRoute } from './register/register.route';
import { settingsRoute } from './settings/settings.route';
import { AccountComponent } from './account.component';

export const accountState: Routes = [
  {
    path: '',
    component: AccountComponent,
    data: {
      authorities: [],
      pageTitle: 'account.title',
    },
  },
  activateRoute,
  passwordRoute,
  passwordResetFinishRoute,
  passwordResetInitRoute,
  registerRoute,
  settingsRoute,
];
