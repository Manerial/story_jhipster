import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIdea, NewIdea } from '../idea.model';

export type PartialUpdateIdea = Partial<IIdea> & Pick<IIdea, 'id'>;

export type EntityResponseType = HttpResponse<IIdea>;
export type EntityArrayResponseType = HttpResponse<IIdea[]>;

@Injectable({ providedIn: 'root' })
export class IdeaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ideas');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(idea: NewIdea): Observable<EntityResponseType> {
    return this.http.post<IIdea>(this.resourceUrl, idea, { observe: 'response' });
  }

  update(idea: IIdea): Observable<EntityResponseType> {
    return this.http.put<IIdea>(`${this.resourceUrl}/${this.getIdeaIdentifier(idea)}`, idea, { observe: 'response' });
  }

  partialUpdate(idea: PartialUpdateIdea): Observable<EntityResponseType> {
    return this.http.patch<IIdea>(`${this.resourceUrl}/${this.getIdeaIdentifier(idea)}`, idea, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIdea>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIdea[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIdeaIdentifier(idea: Pick<IIdea, 'id'>): number {
    return idea.id;
  }

  compareIdea(o1: Pick<IIdea, 'id'> | null, o2: Pick<IIdea, 'id'> | null): boolean {
    return o1 && o2 ? this.getIdeaIdentifier(o1) === this.getIdeaIdentifier(o2) : o1 === o2;
  }

  addIdeaToCollectionIfMissing<Type extends Pick<IIdea, 'id'>>(
    ideaCollection: Type[],
    ...ideasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ideas: Type[] = ideasToCheck.filter(isPresent);
    if (ideas.length > 0) {
      const ideaCollectionIdentifiers = ideaCollection.map(ideaItem => this.getIdeaIdentifier(ideaItem)!);
      const ideasToAdd = ideas.filter(ideaItem => {
        const ideaIdentifier = this.getIdeaIdentifier(ideaItem);
        if (ideaCollectionIdentifiers.includes(ideaIdentifier)) {
          return false;
        }
        ideaCollectionIdentifiers.push(ideaIdentifier);
        return true;
      });
      return [...ideasToAdd, ...ideaCollection];
    }
    return ideaCollection;
  }
}
