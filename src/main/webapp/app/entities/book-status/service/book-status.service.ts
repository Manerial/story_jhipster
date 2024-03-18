import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBookStatus, NewBookStatus } from '../book-status.model';

export type PartialUpdateBookStatus = Partial<IBookStatus> & Pick<IBookStatus, 'id'>;

export type EntityResponseType = HttpResponse<IBookStatus>;
export type EntityArrayResponseType = HttpResponse<IBookStatus[]>;

@Injectable({ providedIn: 'root' })
export class BookStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/book-statuses');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(bookStatus: NewBookStatus): Observable<EntityResponseType> {
    return this.http.post<IBookStatus>(this.resourceUrl, bookStatus, { observe: 'response' });
  }

  update(bookStatus: IBookStatus): Observable<EntityResponseType> {
    return this.http.put<IBookStatus>(`${this.resourceUrl}/${this.getBookStatusIdentifier(bookStatus)}`, bookStatus, {
      observe: 'response',
    });
  }

  partialUpdate(bookStatus: PartialUpdateBookStatus): Observable<EntityResponseType> {
    return this.http.patch<IBookStatus>(`${this.resourceUrl}/${this.getBookStatusIdentifier(bookStatus)}`, bookStatus, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBookStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBookStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBookStatusIdentifier(bookStatus: Pick<IBookStatus, 'id'>): number {
    return bookStatus.id;
  }

  compareBookStatus(o1: Pick<IBookStatus, 'id'> | null, o2: Pick<IBookStatus, 'id'> | null): boolean {
    return o1 && o2 ? this.getBookStatusIdentifier(o1) === this.getBookStatusIdentifier(o2) : o1 === o2;
  }

  addBookStatusToCollectionIfMissing<Type extends Pick<IBookStatus, 'id'>>(
    bookStatusCollection: Type[],
    ...bookStatusesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const bookStatuses: Type[] = bookStatusesToCheck.filter(isPresent);
    if (bookStatuses.length > 0) {
      const bookStatusCollectionIdentifiers = bookStatusCollection.map(bookStatusItem => this.getBookStatusIdentifier(bookStatusItem)!);
      const bookStatusesToAdd = bookStatuses.filter(bookStatusItem => {
        const bookStatusIdentifier = this.getBookStatusIdentifier(bookStatusItem);
        if (bookStatusCollectionIdentifiers.includes(bookStatusIdentifier)) {
          return false;
        }
        bookStatusCollectionIdentifiers.push(bookStatusIdentifier);
        return true;
      });
      return [...bookStatusesToAdd, ...bookStatusCollection];
    }
    return bookStatusCollection;
  }
}
