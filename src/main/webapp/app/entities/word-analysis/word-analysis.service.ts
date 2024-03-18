import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWordAnalysis } from 'app/shared/model/word-analysis.model';

type EntityResponseType = HttpResponse<IWordAnalysis>;
type EntityArrayResponseType = HttpResponse<IWordAnalysis[]>;

@Injectable({ providedIn: 'root' })
export class WordAnalysisService {
  public resourceUrl = SERVER_API_URL + 'api/word-analyses';

  constructor(protected http: HttpClient) {}

  create(wordAnalysis: IWordAnalysis): Observable<EntityResponseType> {
    return this.http.post<IWordAnalysis>(this.resourceUrl, wordAnalysis, { observe: 'response' });
  }

  update(wordAnalysis: IWordAnalysis): Observable<EntityResponseType> {
    return this.http.put<IWordAnalysis>(this.resourceUrl, wordAnalysis, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWordAnalysis>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWordAnalysis[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
