import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, HostListener } from '@angular/core';
import { BookService } from 'app/entities/book/book.service';
import { IBook } from 'app/shared/model/book.model';
import { JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.scss'],
})
export class LibraryComponent implements OnInit {
  searchText = '';
  innerWidth: number = window.innerWidth;
  books: IBook[] = [];
  collapseBooks: boolean[] = [];
  isDownloading: boolean[] = [];

  constructor(
    public bookService: BookService,
    public alertService: JhiAlertService // private navbarService: NavbarService
  ) {}

  ngOnInit(): void {
    this.onResize();
    this.bookService.query().subscribe((res: HttpResponse<IBook[]>) => {
      this.books = res.body || [];
      // this.books = this.books.concat(this.books).concat(this.books).concat(this.books).concat(this.books);
      this.books.forEach(book => {
        if (book.id !== undefined) {
          this.isDownloading[book.id] = false;
          this.collapseBooks[book.id] = true;
        }
      });
      // this.navbarService.clearAllItems();
    });
    /* this.navbarService.getCurrentSearch().subscribe(search => {
      this.searchText = search;
    }); */
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
    if (bookElement.className.includes('rotating')) {
      return;
    }
    bookElement.className = bookElement.className.includes('bk-bookdefault') ? 'bk-book bk-viewback' : 'bk-book bk-bookdefault';
  }

  @HostListener('window:resize')
  onResize(): void {
    this.innerWidth = window.innerWidth;
  }

  downloadBook(id: number, name: string): void {
    if (this.isDownloading.includes(true)) {
      this.alertService.addAlert({ type: 'danger', msg: 'library.export.ongoing', timeout: 5000 }, []);
      return;
    }

    this.isDownloading[id] = true;
    this.bookService.download(id).subscribe(data => {
      if (data.body != null) {
        this.isDownloading[id] = false;
        const downloadURL = window.URL.createObjectURL(data.body);
        const anchor = document.createElement('a');
        anchor.download = name + '.docx';
        anchor.href = downloadURL;
        anchor.click();
      }
    });
  }
}
