import { Component, OnDestroy, OnInit } from '@angular/core';
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

  constructor(public readerService: ReaderService, public utilService: UtilService) {}

  ngOnInit(): void {
    this.readerService.book.subscribe(book => {
      this.book = book;
      this.chapterSubscriber = this.readerService.currentChapterIdObs.subscribe(chapterId => {
        if (chapterId !== '') {
          this.loadChapter();
        } else if (this.chapter.id === 0) {
          this.readerService.changeChapter(book.parts[0].chapters[0].id);
        }
      });
    });
  }

  ngOnDestroy(): void {
    this.readerService.changeChapter(this.chapter.id);
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
    this.book.parts.forEach(part => {
      part.chapters.forEach(chapter => {
        if (chapter.id === this.readerService.currentChapterId) {
          currentChapter = chapter;
        }
      });
    });
    return currentChapter;
  }
}
