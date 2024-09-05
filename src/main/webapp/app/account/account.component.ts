import { Component, OnInit } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { BookService } from 'app/entities/book/book.service';
import { IBook } from 'app/shared/model/book.model';

@Component({
  selector: 'jhi-account',
  templateUrl: './account.component.html',
})
export class AccountComponent implements OnInit {
  account!: Account;
  books: IBook[] = [];

  constructor(private accountService: AccountService, private bookService: BookService) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.account = account;
        this.bookService.findAllByAuthor(account.login).subscribe(books => {
          if (books) {
            this.books = books.body || [];
          }
        });
      }
    });
  }

  login(): void {}
}
