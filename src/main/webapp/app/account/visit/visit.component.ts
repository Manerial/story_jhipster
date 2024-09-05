import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { User } from 'app/core/user/user.model';
import { BookService } from 'app/entities/book/book.service';
import { IBook } from 'app/shared/model/book.model';

@Component({
  selector: 'jhi-visit',
  templateUrl: './visit.component.html',
})
export class VisitComponent implements OnInit {
  user: User | null = null;
  books: IBook[] = [];

  constructor(private route: ActivatedRoute, private bookService: BookService) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ user }) => {
      this.user = user;
      this.bookService.findAllByAuthor(user.login).subscribe(books => {
        if (books) {
          this.books = books.body || [];
        }
      });
    });
  }
}
