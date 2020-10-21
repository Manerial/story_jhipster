import { NgModule } from '@angular/core';
import { NidAuxHistoiresSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { BookFilterPipe } from './util/search.pipe';

@NgModule({
  imports: [NidAuxHistoiresSharedLibsModule],
  declarations: [
    FindLanguageFromKeyPipe,
    BookFilterPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
  ],
  entryComponents: [LoginModalComponent],
  exports: [
    NidAuxHistoiresSharedLibsModule,
    FindLanguageFromKeyPipe,
    BookFilterPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
  ],
})
export class NidAuxHistoiresSharedModule {}
