import { AfterViewInit, Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CommentService } from 'app/entities/comment/comment.service';
import { IBook } from 'app/shared/model/book.model';
import { Comment } from 'app/shared/model/comment.model';

@Component({
  templateUrl: './add-comment.component.html',
})
export class AddCommentDialogComponent implements AfterViewInit {
  book?: IBook;
  text = '';

  constructor(protected commentService: CommentService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  ngAfterViewInit(): void {
    document.getElementById('enterText')?.focus();
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmComment(): void {
    const comment = new Comment();
    comment.bookId = this.book?.id || 0;
    comment.text = this.text;
    comment.id = NaN;

    this.commentService.create(comment).subscribe(() => {
      this.activeModal.close();
    });
  }
}
