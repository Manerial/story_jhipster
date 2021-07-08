import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBookStatus } from 'app/shared/model/bookStatus.model';
import { BookStatusService } from './bookStatus.service';

@Component({
  templateUrl: './bookStatus-delete-dialog.component.html',
})
export class BookStatusDeleteDialogComponent {
  bookStatus?: IBookStatus;

  constructor(
    protected bookStatusService: BookStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bookStatusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bookStatusListModification');
      this.activeModal.close();
    });
  }
}
