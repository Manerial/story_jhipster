import { Component, OnInit, HostListener } from '@angular/core';
import { ReaderService } from 'app/library/reader/reader.service';
import { Book, IBook } from 'app/shared/model/book.model';
import { NavbarService } from 'app/shared/util/search.service';

@Component({
  selector: 'jhi-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.scss', './sidebar.responsive.scss'],
})
export class SidebarComponent implements OnInit {
  public book: IBook = new Book();
  public forceOpenAllImage = false;
  public forceOpenAllSmall = false;
  public collapseSummary = false;
  public collapseParts: boolean[] = [];
  public isLoading = true;
  public innerWidth: number = window.innerWidth;
  private saveScroll = 0;

  constructor(public readerService: ReaderService, private navbarService: NavbarService) {}

  ngOnInit(): void {
    this.innerWidth = window.innerWidth;
    this.checkCollapseSommaire();
    this.readerService.book.subscribe(book => {
      this.book = book;
      this.isLoading = false;
      this.book.parts.forEach(part => {
        this.collapseParts[part.id] = true;
      });
    });

    this.navbarService.getCurrentIsViewBook().subscribe(isViewBook => {
      this.forceOpenAllImage = !isViewBook;
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
    const force = this.innerWidth <= 850;
    this.forceOpenAllSmall = force;
    this.collapseSummary = force;
    this.toggleAddaptSummary();
  }

  isPartCollapse(id: number): boolean {
    return this.collapseParts[id] && !this.forceOpenAll();
  }

  toggleSommaire(): void {
    const currentScroll = this.readerService.scroll(0);
    this.collapseSummary = !this.collapseSummary;
    if (this.collapseSummary) {
      this.readerService.scroll(this.saveScroll);
      this.saveScroll = 0;
    } else {
      this.saveScroll = currentScroll;
    }
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

  forceOpenAll(): boolean {
    return this.forceOpenAllImage || this.forceOpenAllSmall;
  }

  toggleCollapsePart(id: number): void {
    if (!this.forceOpenAll()) {
      this.collapseParts[id] = !this.collapseParts[id];
    }
  }
}
