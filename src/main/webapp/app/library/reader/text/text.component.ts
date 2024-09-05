import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { BookStatusService } from 'app/entities/bookStatus/bookStatus.service';
import { Book, IBook } from 'app/shared/model/book.model';
import { Chapter, IChapter } from 'app/shared/model/chapter.model';
import { IScene } from 'app/shared/model/scene.model';
import { UtilService } from 'app/shared/util/util.service';
import { Subscription } from 'rxjs';
import { ReaderService } from '../reader.service';

@Component({
  selector: 'jhi-reader-text',
  templateUrl: './text.component.html',
  styleUrls: ['./text.scss'],
})
export class TextComponent implements OnInit, OnDestroy {
  public book: IBook = new Book();
  public chapter: IChapter = new Chapter();
  private chapterSubscriber: Subscription = new Subscription();
  private isArrowDown = false;
  private isArrowUp = false;

  constructor(public readerService: ReaderService, public utilService: UtilService, public bookStatusService: BookStatusService) {}

  ngOnInit(): void {
    this.readerService.book.subscribe(book => {
      this.book = book;
      this.chapterSubscriber = this.readerService.currentChapterIdObs.subscribe(chapterId => {
        if (chapterId !== '') {
          // Si on est en train de lire
          this.loadChapter();
        } else if (this.chapter.id === 0) {
          this.bookStatusService.findByBook(this.book.id!).subscribe(
            bookStatus => {
              if (bookStatus.body) {
                // Si on a déjà lu un chapitre
                this.readerService.changeChapter(bookStatus.body.curentChapterId!);
              }
            },
            () => {
              // Chapitre 0 du livre
              this.readerService.changeChapter(this.book.parts![0].chapters![0].id!);
            }
          );
        }
      });
    });
  }

  ngOnDestroy(): void {
    this.readerService.changeChapter(this.chapter.id!);
    this.chapterSubscriber.unsubscribe();
  }

  loadChapter(): void {
    this.chapter = this.getCurentChapter();
    this.utilService.scrollContainerLimitTop(0);
  }

  getSceneTextSplit(scene: IScene): string[] {
    return scene.text.split('\r\n');
  }

  getCurentChapter(): IChapter {
    let currentChapter: IChapter = new Chapter();
    this.book.parts?.forEach(part => {
      part.chapters?.forEach(chapter => {
        if (chapter.id === this.readerService.currentChapterId) {
          currentChapter = chapter;
        }
      });
    });
    return currentChapter;
  }

  prev(): void {
    this.readerService.changeChapterNumber(-1);
  }

  next(): void {
    this.readerService.changeChapterNumber(1);
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEventDown(event: KeyboardEvent): void {
    switch (event.key) {
      case 'ArrowRight':
        this.next();
        event.preventDefault();
        break;
      case 'ArrowLeft':
        this.prev();
        event.preventDefault();
        break;
      case 'ArrowUp':
        if (!this.isArrowUp) {
          this.isArrowUp = true;
          this.scrollUpSmooth();
        }
        event.preventDefault();
        break;
      case 'ArrowDown':
        if (!this.isArrowDown) {
          this.isArrowDown = true;
          this.scrollDownSmooth();
        }
        event.preventDefault();
        break;
      default:
        break;
    }
  }

  @HostListener('document:keyup', ['$event'])
  handleKeyboardEventUp(event: KeyboardEvent): void {
    switch (event.key) {
      case 'ArrowUp':
        this.isArrowUp = false;
        event.preventDefault();
        break;
      case 'ArrowDown':
        this.isArrowDown = false;
        event.preventDefault();
        break;
      default:
        break;
    }
  }

  scrollDownSmooth(): void {
    if (this.isArrowDown) {
      document.getElementById('main-container')?.scrollBy({ top: 50, left: 0, behavior: 'smooth' });
      setTimeout(() => {
        this.scrollDownSmooth();
      }, 120);
    }
  }

  scrollUpSmooth(): void {
    if (this.isArrowUp) {
      document.getElementById('main-container')?.scrollBy({ top: -50, left: 0, behavior: 'smooth' });
      setTimeout(() => {
        this.scrollUpSmooth();
      }, 120);
    }
  }
}
