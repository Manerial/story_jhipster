import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPart } from 'app/shared/model/part.model';
import { PartService } from './part.service';

@Component({
  templateUrl: './part-delete-dialog.component.html',
})
export class PartDeleteDialogComponent {
  part?: IPart;

  constructor(protected partService: PartService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partListModification');
      this.activeModal.close();
    });
  }
}
