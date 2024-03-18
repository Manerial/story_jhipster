import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IBookStatus } from '../book-status.model';
import { BookStatusService } from '../service/book-status.service';

@Component({
  standalone: true,
  templateUrl: './book-status-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class BookStatusDeleteDialogComponent {
  bookStatus?: IBookStatus;

  constructor(
    protected bookStatusService: BookStatusService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bookStatusService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
