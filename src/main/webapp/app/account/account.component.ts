import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { BookDeleteDialogComponent } from 'app/entities/book/book-delete-dialog.component';
import { BookService } from 'app/entities/book/book.service';
import { IBook } from 'app/shared/model/book.model';

@Component({
  selector: 'jhi-account',
  templateUrl: './account.component.html',
})
export class AccountComponent implements OnInit {
  account!: Account;
  books: IBook[] = [];

  constructor(private accountService: AccountService, private bookService: BookService, protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.account = account;
        this.getBooks();
      }
    });
  }

  login(): void {}

  delete(book: IBook): void {
    const modalRef = this.modalService.open(BookDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.book = book;
    modalRef.result.then(() => {
      this.getBooks();
    });
  }

  getBooks(): void {
    this.bookService.findAllByAuthor(this.account.login).subscribe(books => {
      if (books) {
        this.books = books.body || [];
      }
    });
  }

  toggleActive(book: IBook): void {
    book.visibility = !book.visibility;
    this.bookService.updateVisibility(book.id).subscribe();
  }
}
