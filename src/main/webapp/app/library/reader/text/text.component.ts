import { Component, OnInit } from '@angular/core';
import { Book, IBook } from 'app/shared/model/book.model';
import { Chapter, IChapter } from 'app/shared/model/chapter.model';
import { IScene } from 'app/shared/model/scene.model';
import { ReaderService } from '../reader.service';

@Component({
  selector: 'jhi-reader-text',
  templateUrl: './text.component.html',
  styleUrls: ['./text.scss'],
})
export class TextComponent implements OnInit {
  public book: IBook = new Book();
  public chapter: IChapter = new Chapter();
  public isLoading = true;

  constructor(public readerService: ReaderService) {}

  ngOnInit(): void {
    this.readerService.book.subscribe(book => {
      this.book = book;
      this.readerService.currentChapterIdObs.subscribe(chapterId => {
        if (chapterId !== '') {
          this.loadChapter();
        }
      });
    });
  }

  loadChapter(): void {
    this.chapter = this.getCurentChapter();
    document.getElementsByClassName('container-limit')[0].scrollTop = 0;
    this.isLoading = false;
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
