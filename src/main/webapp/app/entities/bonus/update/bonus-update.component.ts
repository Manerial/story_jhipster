import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IBonus } from '../bonus.model';
import { BonusService } from '../service/bonus.service';
import { BonusFormService, BonusFormGroup } from './bonus-form.service';

@Component({
  standalone: true,
  selector: 'jhi-bonus-update',
  templateUrl: './bonus-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class BonusUpdateComponent implements OnInit {
  isSaving = false;
  bonus: IBonus | null = null;

  editForm: BonusFormGroup = this.bonusFormService.createBonusFormGroup();

  constructor(
    protected bonusService: BonusService,
    protected bonusFormService: BonusFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bonus }) => {
      this.bonus = bonus;
      if (bonus) {
        this.updateForm(bonus);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bonus = this.bonusFormService.getBonus(this.editForm);
    if (bonus.id !== null) {
      this.subscribeToSaveResponse(this.bonusService.update(bonus));
    } else {
      this.subscribeToSaveResponse(this.bonusService.create(bonus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBonus>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(bonus: IBonus): void {
    this.bonus = bonus;
    this.bonusFormService.resetForm(this.editForm, bonus);
  }
}
