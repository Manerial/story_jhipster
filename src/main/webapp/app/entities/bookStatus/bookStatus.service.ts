import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBookStatus } from 'app/shared/model/bookStatus.model';
import { AccountService } from 'app/core/auth/account.service';

type EntityResponseType = HttpResponse<IBookStatus>;
type EntityArrayResponseType = HttpResponse<IBookStatus[]>;

@Injectable({ providedIn: 'root' })
export class BookStatusService {
  public resourceUrl = SERVER_API_URL + 'api/bookStatuses';

  constructor(protected http: HttpClient, private accountService: AccountService) {}

  create(bookStatus: IBookStatus): Observable<EntityResponseType> {
    return this.http.post<IBookStatus>(this.resourceUrl, bookStatus, { observe: 'response' });
  }

  update(bookStatus: IBookStatus): Observable<EntityResponseType> {
    return this.http.put<IBookStatus>(this.resourceUrl, bookStatus, { observe: 'response' });
  }

  upsert(bookId: number, chapterId: number): Observable<EntityResponseType> {
    if (!this.accountService.isAuthenticated()) {
      throw new Error('Authentication required');
    }
    let httpParams = new HttpParams();
    httpParams = httpParams.append('bookId', bookId.toString());
    httpParams = httpParams.append('chapterId', chapterId.toString());
    return this.http.put<IBookStatus>(`${this.resourceUrl}/saveChapter`, null, { observe: 'response', params: httpParams });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBookStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findByBook(bookId: number): Observable<EntityResponseType> {
    if (!this.accountService.isAuthenticated()) {
      throw new Error('Authentication required');
    }
    return this.http.get<IBookStatus>(`${this.resourceUrl}/book/${bookId}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBookStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
