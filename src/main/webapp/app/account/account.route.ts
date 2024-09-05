import { Routes } from '@angular/router';

import { activateRoute } from './activate/activate.route';
import { passwordRoute } from './password/password.route';
import { passwordResetFinishRoute } from './password-reset/finish/password-reset-finish.route';
import { passwordResetInitRoute } from './password-reset/init/password-reset-init.route';
import { registerRoute } from './register/register.route';
import { settingsRoute } from './settings/settings.route';
import { AccountComponent } from './account.component';
import { Authority } from 'app/shared/constants/authority.constants';

export const accountState: Routes = [
  {
    path: '',
    component: AccountComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'global.menu.account.main',
    },
  },
  activateRoute,
  passwordRoute,
  passwordResetFinishRoute,
  passwordResetInitRoute,
  registerRoute,
  settingsRoute,
];
