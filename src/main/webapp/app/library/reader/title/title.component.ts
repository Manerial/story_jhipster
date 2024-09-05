import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Book, IBook } from 'app/shared/model/book.model';
import { ReaderService } from '../reader.service';

@Component({
  selector: 'jhi-reader-title',
  templateUrl: './title.component.html',
  styleUrls: ['./title.component.scss'],
})
export class TitleComponent implements OnInit {
  public book: IBook = new Book();
  public isLoading = true;

  constructor(public readerService: ReaderService, private titleService: Title) {}

  ngOnInit(): void {
    const bookObserver = this.readerService.getBook();
    if (bookObserver)
      bookObserver.subscribe(book => {
        if (book.body) this.book = book.body;
        this.isLoading = false;
        this.titleService.setTitle(this.book.name);
      });
  }
}
