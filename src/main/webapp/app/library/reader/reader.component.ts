import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { BookService } from 'app/entities/book/book.service';
import { of } from 'rxjs';
import { ReaderService } from './reader.service';

@Component({
  selector: 'jhi-reader',
  templateUrl: './reader.component.html',
})
export class ReaderComponent implements OnInit {
  public isLoaded = false;

  constructor(
    private acRoute: ActivatedRoute,
    private readerService: ReaderService,
    private bookService: BookService,
    private titleService: Title
  ) {}

  ngOnInit(): void {
    this.acRoute.paramMap.subscribe(params => {
      const bookIdStr = params.get('bookId');
      if (bookIdStr === null) {
        throw 'Book Id is null';
      }
      const bookId = parseInt(bookIdStr, 10);
      this.readerService.setBookObservable(this.bookService.find(bookId));

      this.readerService.getBookObservable().subscribe(book => {
        if (book.body) {
          this.readerService.book = of(book.body);
          this.titleService.setTitle(book.body.name);
        }
        this.isLoaded = true;
      });
    });
  }
}
