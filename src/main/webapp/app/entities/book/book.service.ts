import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBook } from 'app/shared/model/book.model';

type EntityResponseType = HttpResponse<IBook>;
type BookResponseType = HttpResponse<Blob>;
type EntityArrayResponseType = HttpResponse<IBook[]>;
type StringArrayResponseType = HttpResponse<string[]>;

@Injectable({ providedIn: 'root' })
export class BookService {
  public resourceUrl = SERVER_API_URL + 'api/books';
  public resourceLightUrl = SERVER_API_URL + 'api/books_light';
  public downloadUrl = SERVER_API_URL + 'api/download';

  constructor(protected http: HttpClient) {}

  create(book: IBook): Observable<EntityResponseType> {
    return this.http.post<IBook>(this.resourceUrl, book, { observe: 'response' });
  }

  update(book: IBook): Observable<EntityResponseType> {
    return this.http.put<IBook>(this.resourceUrl, book, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBook>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findLight(id: number): Observable<EntityResponseType> {
    return this.http.get<IBook>(`${this.resourceLightUrl}/${id}`, { observe: 'response' });
  }

  findAllByAuthor(login: string): Observable<EntityArrayResponseType> {
    return this.http.get<IBook[]>(`${this.resourceUrl}/author/${login}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBook[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  download(id: number, format: string): Observable<BookResponseType> {
    let httpParams = new HttpParams();
    httpParams = httpParams.append('format', format);
    return this.http.get(`${this.downloadUrl}/book/${id}`, { observe: 'response', responseType: 'blob', params: httpParams });
  }

  getFormats(): Observable<StringArrayResponseType> {
    return this.http.get<string[]>(`${this.downloadUrl}/formats`, { observe: 'response' });
  }
}
