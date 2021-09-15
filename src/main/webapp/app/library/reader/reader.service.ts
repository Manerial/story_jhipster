import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BookStatusService } from 'app/entities/bookStatus/bookStatus.service';
import { IBook } from 'app/shared/model/book.model';
import { BehaviorSubject, Observable, of } from 'rxjs';

type EntityResponseType = HttpResponse<IBook>;

@Injectable({
  providedIn: 'root',
})
export class ReaderService {
  private bookObservable: Observable<EntityResponseType> = of();
  public book: Observable<IBook> = of();

  private bookSource = new BehaviorSubject('');
  public currentBookIdObs = this.bookSource.asObservable();
  public currentBookId: number | undefined;

  private partSource = new BehaviorSubject('');
  public currentPartIdObs = this.partSource.asObservable();
  public currentPartId: number | undefined;

  private chapterSource = new BehaviorSubject('');
  public currentChapterIdObs = this.chapterSource.asObservable();
  public currentChapterId: number | undefined;

  private entitySource = new BehaviorSubject('');
  public currentEntityNameObs = this.entitySource.asObservable();
  public currentEntityName: string | undefined;

  constructor(private bookStatusService: BookStatusService) {}

  getBookObservable(): Observable<EntityResponseType> {
    return this.bookObservable;
  }

  setBookObservable(bookObservable: Observable<EntityResponseType>): void {
    this.bookObservable = bookObservable;
  }

  changeBook(bookId: number): void {
    this.partSource.next('');
    this.chapterSource.next('');
    this.currentBookId = bookId;
    this.bookSource.next(bookId.toString());
  }

  changePart(partId: number): void {
    this.bookSource.next('');
    this.chapterSource.next('');
    this.currentPartId = partId;
    this.partSource.next(partId.toString());
  }

  changeChapter(chapterId: number): void {
    this.bookSource.next('');
    this.partSource.next('');
    this.currentChapterId = chapterId;
    this.chapterSource.next(chapterId.toString());
    this.saveCurrentChapter();
  }

  saveCurrentChapter(): void {
    this.bookStatusService.upsert(this.currentBookId!, this.currentChapterId!).subscribe();
  }

  changeEntity(entityName: string): void {
    this.currentEntityName = entityName;
    this.entitySource.next(entityName);
  }

  changeChapterNumber(variance: number): void {
    this.book.subscribe(book => {
      const currentChapterNumber = this.getCurentChapterNumber(book);
      book.parts?.forEach(part => {
        part.chapters?.forEach(chapter => {
          if (chapter.number === currentChapterNumber + variance) {
            this.changeChapter(chapter.id!);
          }
        });
      });
    });
  }

  getCurentChapterNumber(book: IBook): number {
    let chapterNumber = -1;
    book.parts?.forEach(part => {
      part.chapters?.forEach(chapter => {
        if (chapter.id === this.currentChapterId) {
          chapterNumber = chapter.number!;
        }
      });
    });
    return chapterNumber;
  }
}
