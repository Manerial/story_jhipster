import { Injectable } from '@angular/core';
import { IBook } from 'app/shared/model/book.model';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ReaderService {
  private book: Observable<IBook> | undefined;

  private bookSource = new BehaviorSubject('');
  public currentBookIdObs = this.bookSource.asObservable();
  public currentBookId: number | undefined;

  private partSource = new BehaviorSubject('');
  public currentPartIdObs = this.partSource.asObservable();
  public currentPartId: number | undefined;

  private chapterSource = new BehaviorSubject('');
  public currentChapterIdObs = this.chapterSource.asObservable();
  public currentChapterId: number | undefined;

  constructor() {}

  getBook(): Observable<IBook> {
    if (this.book !== undefined) {
      return this.book;
    }
    throw 'Book is undefined';
  }

  setBook(book: Observable<IBook>) {
    this.book = book;
  }

  changeBook(bookId: number) {
    if (this.currentBookId != bookId) {
      this.currentBookId = bookId;
      this.bookSource.next(bookId.toString());
      this.partSource.next('');
      this.chapterSource.next('');
    } else {
      this.bookSource.next(bookId.toString());
    }
  }

  changePart(partId: number) {
    this.currentPartId = partId;
    this.partSource.next(partId.toString());
  }

  changeChapter(chapterId: number) {
    this.currentChapterId = chapterId;
    this.chapterSource.next(chapterId.toString());
  }

  changeChapterNumber(variance: number) {
    this.getBook().subscribe(book => {
      var currentChapterNumber = this.getCurentChapterNumber(book);
      book.parts.forEach(part => {
        part.chapters.forEach(chapter => {
          if (chapter.number === currentChapterNumber + variance) {
            this.changeChapter(chapter.id);
          }
        });
      });
    });
  }

  getCurentChapterNumber(book: IBook): number {
    var chapterNumber = -1;
    book.parts.forEach(part => {
      part.chapters.forEach(chapter => {
        if (chapter.id == this.currentChapterId) {
          chapterNumber = chapter.number;
        }
      });
    });
    return chapterNumber;
  }
}
