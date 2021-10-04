import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBonus } from 'app/shared/model/bonus.model';
import { BonusService } from './bonus.service';

@Component({
  templateUrl: './bonus-delete-dialog.component.html',
})
export class BonusDeleteDialogComponent {
  bonus?: IBonus;

  constructor(protected bonusService: BonusService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bonusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bonusListModification');
      this.activeModal.close();
    });
  }
}
