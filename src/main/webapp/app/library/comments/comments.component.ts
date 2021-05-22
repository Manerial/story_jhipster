import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BookService } from 'app/entities/book/book.service';
import { CommentService } from 'app/entities/comment/comment.service';
import { Book, IBook } from 'app/shared/model/book.model';
import { IComment } from 'app/shared/model/comment.model';

@Component({
  selector: 'jhi-comments',
  templateUrl: './comments.component.html',
})
export class CommentsComponent implements OnInit {
  comments!: IComment[];
  book!: IBook;

  constructor(private acRoute: ActivatedRoute, private commentService: CommentService, private bookService: BookService) {}

  ngOnInit(): void {
    this.acRoute.paramMap.subscribe(params => {
      const bookIdStr = params.get('bookId');
      if (bookIdStr === null) {
        throw 'Book Id is null';
      }
      this.bookService.findLight(Number(bookIdStr)).subscribe(book => {
        this.book = book.body || new Book();
        // eslint-disable-next-line no-console
        console.log(book);
      });
      this.commentService.findByBookId(Number(bookIdStr)).subscribe(comments => {
        this.comments = comments.body || [];
        // eslint-disable-next-line no-console
        console.log(comments);
      });
    });
  }
}
