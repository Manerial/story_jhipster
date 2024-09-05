import { Component, OnInit } from '@angular/core';
import { Book, IBook } from 'app/shared/model/book.model';
import { ReaderService } from '../reader.service';

@Component({
  selector: 'jhi-reader-title',
  templateUrl: './title.component.html',
  styleUrls: ['./title.component.scss'],
})
export class TitleComponent implements OnInit {
  public book: IBook = new Book();

  constructor(public readerService: ReaderService) {}

  ngOnInit(): void {
    this.readerService.book.subscribe(book => {
      this.book = book;
    });
  }
}
