import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICover } from 'app/shared/model/cover.model';
import { CoverService } from './cover.service';

@Component({
  templateUrl: './cover-delete-dialog.component.html',
})
export class CoverDeleteDialogComponent {
  cover?: ICover;

  constructor(protected coverService: CoverService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.coverService.delete(id).subscribe(() => {
      this.eventManager.broadcast('imageListModification');
      this.activeModal.close();
    });
  }
}
