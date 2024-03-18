import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWordAnalysis, NewWordAnalysis } from '../word-analysis.model';

export type PartialUpdateWordAnalysis = Partial<IWordAnalysis> & Pick<IWordAnalysis, 'id'>;

export type EntityResponseType = HttpResponse<IWordAnalysis>;
export type EntityArrayResponseType = HttpResponse<IWordAnalysis[]>;

@Injectable({ providedIn: 'root' })
export class WordAnalysisService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/word-analyses');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(wordAnalysis: NewWordAnalysis): Observable<EntityResponseType> {
    return this.http.post<IWordAnalysis>(this.resourceUrl, wordAnalysis, { observe: 'response' });
  }

  update(wordAnalysis: IWordAnalysis): Observable<EntityResponseType> {
    return this.http.put<IWordAnalysis>(`${this.resourceUrl}/${this.getWordAnalysisIdentifier(wordAnalysis)}`, wordAnalysis, {
      observe: 'response',
    });
  }

  partialUpdate(wordAnalysis: PartialUpdateWordAnalysis): Observable<EntityResponseType> {
    return this.http.patch<IWordAnalysis>(`${this.resourceUrl}/${this.getWordAnalysisIdentifier(wordAnalysis)}`, wordAnalysis, {
      observe: 'response',
    });
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

  getWordAnalysisIdentifier(wordAnalysis: Pick<IWordAnalysis, 'id'>): number {
    return wordAnalysis.id;
  }

  compareWordAnalysis(o1: Pick<IWordAnalysis, 'id'> | null, o2: Pick<IWordAnalysis, 'id'> | null): boolean {
    return o1 && o2 ? this.getWordAnalysisIdentifier(o1) === this.getWordAnalysisIdentifier(o2) : o1 === o2;
  }

  addWordAnalysisToCollectionIfMissing<Type extends Pick<IWordAnalysis, 'id'>>(
    wordAnalysisCollection: Type[],
    ...wordAnalysesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const wordAnalyses: Type[] = wordAnalysesToCheck.filter(isPresent);
    if (wordAnalyses.length > 0) {
      const wordAnalysisCollectionIdentifiers = wordAnalysisCollection.map(
        wordAnalysisItem => this.getWordAnalysisIdentifier(wordAnalysisItem)!,
      );
      const wordAnalysesToAdd = wordAnalyses.filter(wordAnalysisItem => {
        const wordAnalysisIdentifier = this.getWordAnalysisIdentifier(wordAnalysisItem);
        if (wordAnalysisCollectionIdentifiers.includes(wordAnalysisIdentifier)) {
          return false;
        }
        wordAnalysisCollectionIdentifiers.push(wordAnalysisIdentifier);
        return true;
      });
      return [...wordAnalysesToAdd, ...wordAnalysisCollection];
    }
    return wordAnalysisCollection;
  }
}
