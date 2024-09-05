import { HttpResponse } from '@angular/common/http';
import { Component, HostListener, OnInit } from '@angular/core';
import { BookService } from 'app/entities/book/book.service';
import { IBook } from 'app/shared/model/book.model';
import { JhiAlertService } from 'ng-jhipster';
import { ResponsiveService } from 'app/shared/util/responsive.service';
import { NavbarService } from 'app/shared/util/search.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DownloadBookDialogComponent } from './download-book-dialog.component';

@Component({
  selector: 'jhi-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.scss'],
})
export class LibraryComponent implements OnInit {
  searchText = '';
  books: IBook[] = [];
  collapseBooks: boolean[] = [];
  format = 'pdf';
  test = true;

  constructor(
    public bookService: BookService,
    public alertService: JhiAlertService,
    public responsiveService: ResponsiveService,
    private navbarService: NavbarService,
    protected modalService: NgbModal
  ) {}

  @HostListener('window:resize')
  onResize(): void {
    this.responsiveService.onResize();
  }

  ngOnInit(): void {
    this.bookService.query().subscribe((res: HttpResponse<IBook[]>) => {
      this.books = res.body || [];
      this.books.forEach(book => {
        if (book.id !== undefined) {
          this.collapseBooks[book.id] = true;
        }
      });
    });
    this.navbarService.getCurrentSearch().subscribe(search => {
      this.searchText = search;
    });
  }

  testlot(nb: number): number[] {
    const res = [];
    for (let i = 0; i < nb; i++) {
      res.push(i);
    }
    return res;
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

  favorits(book: IBook): void {
    this.test = !this.test;
  }
}
