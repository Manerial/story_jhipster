import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IBonus } from '../bonus.model';
import { BonusService } from '../service/bonus.service';

@Component({
  standalone: true,
  templateUrl: './bonus-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class BonusDeleteDialogComponent {
  bonus?: IBonus;

  constructor(
    protected bonusService: BonusService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bonusService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
