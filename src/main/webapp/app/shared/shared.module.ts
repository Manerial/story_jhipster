import { NgModule } from '@angular/core';
import { NidAuxHistoiresSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { HasNoAuthorityDirective } from './auth/has-no-authority.directive';
import { BookFilterPipe, AuthorFilterPipe } from './util/search.pipe';
import { InputPattern } from './util/input-pattern';
import { SecretCodeDirective } from './secret/secret-code.directive';

@NgModule({
  imports: [NidAuxHistoiresSharedLibsModule],
  declarations: [
    FindLanguageFromKeyPipe,
    BookFilterPipe,
    AuthorFilterPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    HasNoAuthorityDirective,
    SecretCodeDirective,
  ],
  entryComponents: [LoginModalComponent],
  exports: [
    NidAuxHistoiresSharedLibsModule,
    FindLanguageFromKeyPipe,
    BookFilterPipe,
    AuthorFilterPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    HasNoAuthorityDirective,
    SecretCodeDirective,
  ],
  providers: [InputPattern],
})
export class NidAuxHistoiresSharedModule {}
