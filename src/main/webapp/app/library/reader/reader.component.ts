import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BookService } from 'app/entities/book/book.service';
import { ReaderService } from './reader.service';

@Component({
  selector: 'jhi-reader',
  templateUrl: './reader.component.html',
})
export class ReaderComponent implements OnInit {
  constructor(private acRoute: ActivatedRoute, private readerService: ReaderService, private bookService: BookService) {}

  ngOnInit(): void {
    this.acRoute.paramMap.subscribe(params => {
      const bookIdStr = params.get('bookId');
      if (bookIdStr === null) {
        throw 'Book Id is null';
      }
      const bookId = parseInt(bookIdStr, 10);
      this.readerService.setBook(this.bookService.find(bookId));
    });
  }
}
