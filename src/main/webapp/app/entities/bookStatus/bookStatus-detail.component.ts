import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBookStatus } from 'app/shared/model/bookStatus.model';

@Component({
  selector: 'jhi-library-detail',
  templateUrl: './bookStatus-detail.component.html',
})
export class BookStatusDetailComponent implements OnInit {
  bookStatus: IBookStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookStatus }) => (this.bookStatus = bookStatus));
  }

  previousState(): void {
    window.history.back();
  }
}
