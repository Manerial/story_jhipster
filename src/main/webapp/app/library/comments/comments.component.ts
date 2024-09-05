import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from 'app/core/auth/account.service';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { BookService } from 'app/entities/book/book.service';
import { CommentService } from 'app/entities/comment/comment.service';
import { Book, IBook } from 'app/shared/model/book.model';
import { IComment } from 'app/shared/model/comment.model';
import { TitleService } from 'app/shared/util/title.service';
import { AddCommentDialogComponent } from './add-comment.component';

@Component({
  selector: 'jhi-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.scss'],
})
export class CommentsComponent implements OnInit {
  comments!: IComment[];
  book!: IBook;

  constructor(
    private acRoute: ActivatedRoute,
    private commentService: CommentService,
    private bookService: BookService,
    private accountService: AccountService,
    private loginModalSercice: LoginModalService,
    protected modalService: NgbModal,
    private titleService: TitleService
  ) {}

  ngOnInit(): void {
    this.acRoute.paramMap.subscribe(params => {
      const bookIdStr = params.get('bookId');
      if (bookIdStr === null) {
        throw 'Book Id is null';
      }
      this.bookService.findLight(Number(bookIdStr)).subscribe(book => {
        this.book = book.body || new Book();
        this.titleService.replaceTitle('comments.title', { entityName: this.book.name });
      });
      this.commentService.findByBookId(Number(bookIdStr)).subscribe(comments => {
        this.comments = comments.body || [];
      });
    });
  }

  addComment(): void {
    if (!this.accountService.isAuthenticated()) {
      this.loginModalSercice.open(() => this.openCommentModal());
    } else {
      this.openCommentModal();
    }
  }

  openCommentModal(): void {
    const modalRef = this.modalService.open(AddCommentDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.book = this.book;
    modalRef.result
      .then(() => {
        this.commentService.findByBookId(Number(this.book.id)).subscribe(comments => {
          this.comments = comments.body || this.comments;
        });
      })
      .catch(() => {});
  }
}
