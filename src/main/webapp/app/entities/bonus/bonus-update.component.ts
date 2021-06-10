import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBonus, Bonus } from 'app/shared/model/bonus.model';
import { BonusService } from './bonus.service';

@Component({
  selector: 'jhi-bonus-update',
  templateUrl: './bonus-update.component.html',
})
export class BonusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    extension: [],
  });

  constructor(protected bonusService: BonusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bonus }) => {
      this.updateForm(bonus);
    });
  }

  updateForm(bonus: IBonus): void {
    this.editForm.patchValue({
      id: bonus.id,
      name: bonus.name,
      extension: bonus.extension,
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

  private createFromForm(): any {
    return {
      ...new Bonus(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      extension: this.editForm.get(['extension'])!.value,
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
}
