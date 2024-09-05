import { AfterViewInit, Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book/book.service';

@Component({
  templateUrl: './download-book-dialog.component.html',
})
export class DownloadBookDialogComponent implements OnInit, AfterViewInit {
  book?: IBook;
  formatList: string[] = [];
  format = 'pdf';

  constructor(protected bookService: BookService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  ngOnInit(): void {
    this.bookService.getFormats().subscribe(data => {
      if (data.body != null) {
        this.formatList = data.body;
      }
    });
  }

  ngAfterViewInit(): void {
    document.getElementById('selectFormat')?.focus();
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDownload(): void {
    this.bookService.download(this.book?.id!, this.format).subscribe(data => {
      if (data.body != null) {
        const downloadURL = window.URL.createObjectURL(data.body);
        const anchor = document.createElement('a');
        anchor.download = this.book?.name! + '.' + this.format;
        anchor.href = downloadURL;
        anchor.click();
        this.activeModal.close();
      }
    });
  }
}
