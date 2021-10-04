import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IUser, User } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { Book, IBook } from 'app/shared/model/book.model';

import { IComment } from 'app/shared/model/comment.model';
import { BookService } from '../book/book.service';

@Component({
  selector: 'jhi-comment-detail',
  templateUrl: './comment-detail.component.html',
})
export class CommentDetailComponent implements OnInit {
  comment: IComment | null = null;
  user: IUser = new User();
  book: IBook = new Book();

  constructor(protected activatedRoute: ActivatedRoute, protected bookService: BookService, protected userService: UserService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comment }) => {
      this.comment = comment;
      this.bookService.find(comment.bookId).subscribe((res: HttpResponse<IBook>) => (this.book = res.body || this.book));
      this.userService.find(comment.userLogin).subscribe();
    });
  }

  previousState(): void {
    window.history.back();
  }
}
