import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBook } from 'app/shared/model/book.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { BookService } from './book.service';
import { BookDeleteDialogComponent } from './book-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { Authority } from 'app/shared/constants/authority.constants';

@Component({
  selector: 'jhi-book',
  templateUrl: './book.component.html',
})
export class BookComponent implements OnInit, OnDestroy {
  books?: IBook[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected bookService: BookService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private accountService: AccountService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    if (!this.accountService.isAuthenticated()) {
      return;
    }
    this.accountService.identity().subscribe(account => {
      if (account) {
        const pageToLoad: number = page || this.page || 1;
        const query = {
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        };
        if (account.authorities.includes(Authority.ADMIN)) {
          this.bookService.query(query).subscribe(
            (res: HttpResponse<IBook[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
            () => this.onError()
          );
        } else {
          this.bookService.findAllByAuthor(account.login).subscribe(
            (res: HttpResponse<IBook[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
            () => this.onError()
          );
        }
      }
    });
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInBooks();
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBook): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBooks(): void {
    this.eventSubscriber = this.eventManager.subscribe('bookListModification', () => this.loadPage());
  }

  delete(book: IBook): void {
    const modalRef = this.modalService.open(BookDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.book = book;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IBook[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/entities/book'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.books = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
