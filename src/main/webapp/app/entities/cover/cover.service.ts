import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICover } from 'app/shared/model/cover.model';

type EntityResponseType = HttpResponse<ICover>;
type EntityArrayResponseType = HttpResponse<ICover[]>;
type CoverResponseType = HttpResponse<Blob>;

@Injectable({ providedIn: 'root' })
export class CoverService {
  public resourceUrl = SERVER_API_URL + 'api/covers';
  public downloadUrl = SERVER_API_URL + 'api/download';

  constructor(protected http: HttpClient) {}

  create(cover: ICover): Observable<EntityResponseType> {
    return this.http.post<ICover>(this.resourceUrl, cover, { observe: 'response' });
  }

  update(cover: ICover): Observable<EntityResponseType> {
    return this.http.put<ICover>(this.resourceUrl, cover, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICover>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICover[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAllCoversByAuthorId(id: number): Observable<EntityArrayResponseType> {
    return this.http.get<ICover[]>(`${this.resourceUrl}/author/${id}`, { observe: 'response' });
  }

  download(id: number): Observable<CoverResponseType> {
    return this.http.get(`${this.downloadUrl}/cover/${id}`, { observe: 'response', responseType: 'blob' });
  }
}
