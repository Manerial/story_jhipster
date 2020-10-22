import { Component, OnInit, HostListener } from '@angular/core';
import { ReaderService } from 'app/library/reader/reader.service';
import { Book, IBook } from 'app/shared/model/book.model';

@Component({
  selector: 'jhi-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.scss'],
})
export class SidebarComponent implements OnInit {
  public book: IBook = new Book();
  public forceOpenAll = false;
  public collapseSummary = false;
  public collapseParts: boolean[] = [];
  public isLoading = true;
  public innerWidth: number = window.innerWidth;

  constructor(public readerService: ReaderService) {}

  ngOnInit(): void {
    this.innerWidth = window.innerWidth;
    this.checkCollapseSommaire();
    const bookObserver = this.readerService.getBook();
    if (bookObserver)
      bookObserver.subscribe(book => {
        if (book.body) this.book = book.body;
        this.isLoading = false;
        this.book.parts.forEach(part => {
          this.collapseParts[part.id] = true;
        });
      });
  }

  changeBook(id: number): void {
    this.readerService.changeBook(id);
    this.checkCollapseSommaire();
  }

  changePart(id: number): void {
    this.readerService.changePart(id);
    this.checkCollapseSommaire();
  }

  changeChapter(id: number): void {
    this.readerService.changeChapter(id);
    this.checkCollapseSommaire();
  }

  @HostListener('window:resize')
  onResize(): void {
    this.innerWidth = window.innerWidth;
    this.checkCollapseSommaire();
  }

  checkCollapseSommaire(): void {
    if (this.innerWidth <= 800) {
      this.forceOpenAll = true;
      this.collapseSummary = true;
    }
    this.toggleAddaptSummary();
  }

  isPartCollapse(id: number): boolean {
    return this.collapseParts[id] && !this.forceOpenAll;
  }

  toggleSommaire(): void {
    this.collapseSummary = !this.collapseSummary;
    this.toggleAddaptSummary();
  }

  toggleAddaptSummary(): void {
    const addaptSummary = document.getElementsByClassName('addapt-summary');
    for (let i = 0; i < addaptSummary.length; i++) {
      const item = addaptSummary.item(i);
      if (item === null) {
        throw 'error';
      }
      if (this.collapseSummary) {
        item.className = item.className.replace(/addapt-summary-open/g, '');
      } else {
        item.className = item.className + ' addapt-summary-open';
      }
    }
  }
}
