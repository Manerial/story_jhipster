import { Component, ElementRef, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IBonus, Bonus } from 'app/shared/model/bonus.model';
import { BonusService } from './bonus.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { BookService } from '../book/book.service';
import { IBook } from 'app/shared/model/book.model';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

@Component({
  selector: 'jhi-bonus-update',
  templateUrl: './bonus-update.component.html',
})
export class BonusUpdateComponent implements OnInit {
  isSaving = false;
  books: IBook[] = [];
  account!: Account;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    ownerId: [null, [Validators.required]],
    ownerLogin: [null, [Validators.required]],
    file: [null, [Validators.required]],
    fileContentType: [],
    description: [],
    bookId: [null, [Validators.required]],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected elementRef: ElementRef,
    protected bonusService: BonusService,
    protected bookService: BookService,
    private accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.account = account;
        this.activatedRoute.data.subscribe(({ bonus }) => {
          this.updateForm(bonus);
          this.bookService.findAllByAuthor(account.login).subscribe((res: HttpResponse<IBook[]>) => (this.books = res.body || []));
        });
      }
    });
  }

  updateForm(bonus: IBonus): void {
    this.editForm.patchValue({
      id: bonus.id,
      name: bonus.name,
      ownerId: bonus.ownerId !== 0 ? bonus.ownerId : this.account.id,
      ownerLogin: bonus.ownerLogin !== '' ? bonus.ownerLogin : this.account.login,
      file: bonus.data,
      fileContentType: bonus.extension,
      description: bonus.description,
      bookId: bonus.bookId !== 0 ? bonus.bookId : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bonus = this.createFromForm();
    if (bonus.id !== undefined) {
      this.subscribeToSaveResponse(this.bonusService.update(bonus));
    } else {
      delete bonus.id;
      this.subscribeToSaveResponse(this.bonusService.create(bonus));
    }
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  private createFromForm(): any {
    return {
      ...new Bonus(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      ownerId: this.editForm.get(['ownerId'])!.value,
      ownerLogin: this.editForm.get(['ownerLogin'])!.value,
      data: this.editForm.get(['file'])!.value,
      extension: this.editForm.get(['fileContentType'])!.value,
      description: this.editForm.get(['description'])!.value,
      bookId: this.editForm.get(['bookId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBonus>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('nidAuxHistoiresApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputData(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }
}
