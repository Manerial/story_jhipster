import { Component, OnInit, HostListener } from '@angular/core';
import { ReaderService } from 'app/library/reader/reader.service';
import { Book, IBook } from 'app/shared/model/book.model';
import { ResponsiveService } from 'app/shared/util/responsive.service';
import { UtilService } from 'app/shared/util/util.service';

@Component({
  selector: 'jhi-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.scss', './sidebar.responsive.scss'],
})
export class SidebarComponent implements OnInit {
  public book: IBook = new Book();
  public forceOpenAllSmall = false;
  public collapseSummary = false;
  public collapseParts: boolean[] = [];
  public isLoading = true;
  public innerWidth: number = window.innerWidth;
  private saveScroll = 0;

  constructor(public readerService: ReaderService, private utilService: UtilService, private responsiveService: ResponsiveService) {}

  ngOnInit(): void {
    this.innerWidth = window.innerWidth;
    this.checkCollapseSommaire();
    this.readerService.book.subscribe(book => {
      this.book = book;
      this.isLoading = false;
      this.book.parts?.forEach(part => {
        this.collapseParts[part.id!] = true;
      });
    });
  }

  changeBook(id: number, name: string): void {
    this.readerService.changeBook(id);
    this.readerService.changeEntity(name);
    this.checkCollapseSommaire();
  }

  changePart(id: number, name: string): void {
    this.readerService.changePart(id);
    this.readerService.changeEntity(name);
    this.checkCollapseSommaire();
  }

  changeChapter(id: number, name: string): void {
    this.readerService.changeChapter(id);
    this.readerService.changeEntity(name);
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
    this.collapseSummary = !this.collapseSummary;

    if (!this.responsiveService.isBigScreen) {
      let currentScroll = 0;
      currentScroll = this.utilService.scrollContainerLimitTop(0);
      if (this.collapseSummary) {
        this.utilService.scrollContainerLimitTop(this.saveScroll);
        this.saveScroll = 0;
      } else {
        this.saveScroll = currentScroll;
      }
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
    return this.forceOpenAllSmall;
  }

  toggleCollapsePart(id: number): void {
    if (!this.forceOpenAll()) {
      this.collapseParts[id] = !this.collapseParts[id];
    }
  }
}
