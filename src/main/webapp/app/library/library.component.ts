import { HttpResponse } from '@angular/common/http';
import { Component, HostListener, OnInit } from '@angular/core';
import { BookService } from 'app/entities/book/book.service';
import { IBook } from 'app/shared/model/book.model';
import { JhiAlertService } from 'ng-jhipster';
import { ResponsiveService } from 'app/shared/util/responsive.service';
import { NavbarService } from 'app/shared/util/search.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DownloadBookDialogComponent } from './download-book-dialog.component';
import { BookStatusService } from 'app/entities/bookStatus/bookStatus.service';
import { BookStatus, IBookStatus } from 'app/shared/model/bookStatus.model';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.scss'],
})
export class LibraryComponent implements OnInit {
  searchText = '';
  books: IBook[] = [];
  bookStatusList: IBookStatus[] = [];
  collapseBooks: boolean[] = [];
  format = 'pdf';
  account!: Account;
  favorits = false;

  constructor(
    public bookService: BookService,
    public bookStatusService: BookStatusService,
    public accountService: AccountService,
    public alertService: JhiAlertService,
    public responsiveService: ResponsiveService,
    public navbarService: NavbarService,
    public modalService: NgbModal,
    public router: Router
  ) {}

  @HostListener('window:resize')
  onResize(): void {
    this.responsiveService.onResize();
  }

  ngOnInit(): void {
    this.favorits = this.router.url.indexOf('favorits') > 0;

    if (this.favorits) {
      this.getFavorits();
    } else {
      this.getBooks();
    }
    this.getSearchAccount();
  }

  getSearchAccount(): void {
    this.navbarService.getCurrentSearch().subscribe(search => {
      this.searchText = search;
    });

    this.accountService.identity().subscribe(account => {
      if (account) {
        this.account = account;
      }
    });
  }

  getBooks(): void {
    this.bookService.query().subscribe((books: HttpResponse<IBook[]>) => {
      this.books = books.body || [];
      this.books.forEach(book => {
        if (book.id !== undefined) {
          this.collapseBooks[book.id] = true;
        }
      });
      this.bookStatusService.query().subscribe((bookStatusList: HttpResponse<IBookStatus[]>) => {
        this.bookStatusList = bookStatusList.body || [];
      });
    });
  }

  getFavorits(): void {
    this.bookService.findAllFavorits().subscribe((books: HttpResponse<IBook[]>) => {
      this.books = books.body || [];
      this.books.forEach(book => {
        if (book.id !== undefined) {
          this.collapseBooks[book.id] = true;
        }
      });
      this.bookStatusService.query().subscribe((bookStatusList: HttpResponse<IBookStatus[]>) => {
        this.bookStatusList = bookStatusList.body || [];
      });
    });
  }

  flip(event: MouseEvent): void {
    event.preventDefault();
    const bookElement = event.currentTarget as HTMLDivElement;
    bookElement.className = bookElement.className.includes('bk-bookdefault') ? 'bk-book bk-viewback' : 'bk-book bk-bookdefault';
  }

  downloadBook(book: IBook): void {
    const modalRef = this.modalService.open(DownloadBookDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.book = book;
  }

  findBookStatusByBookId(bookId: number): IBookStatus {
    const resultList = this.bookStatusList.filter(bookStatus => bookStatus.bookId === bookId);
    if (resultList.length === 0) {
      const result = new BookStatus();
      result.bookId = bookId;
      result.userId = this.account.id;
      this.bookStatusList.push(result);
      return result;
    } else {
      return resultList[0];
    }
  }

  toggleFavorits(bookId: number): void {
    const bookStatus: any = this.findBookStatusByBookId(bookId);
    bookStatus.favorit = !bookStatus.favorit;
    if (bookStatus.id === 0) {
      delete bookStatus.id;
      delete bookStatus.curentChapterId;
      this.bookStatusService.create(bookStatus).subscribe();
    } else {
      this.bookStatusService.update(bookStatus).subscribe();
    }
  }
}
