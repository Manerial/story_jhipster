import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIdea } from 'app/shared/model/idea.model';
import { IWriting } from 'app/shared/model/idea-generator/writing.model';
import { IPersona } from 'app/shared/model/idea-generator/persona.model';
import { ILocation } from 'app/shared/model/idea-generator/location.model';
import { IObject } from 'app/shared/model/idea-generator/object.model';

type EntityResponseType = HttpResponse<IIdea>;
type EntityArrayResponseType = HttpResponse<IIdea[]>;

@Injectable({ providedIn: 'root' })
export class IdeaService {
  public resourceUrl = SERVER_API_URL + 'api/ideas';

  constructor(protected http: HttpClient) {}

  create(idea: IIdea): Observable<EntityResponseType> {
    return this.http.post<IIdea>(this.resourceUrl, idea, { observe: 'response' });
  }

  update(idea: IIdea): Observable<EntityResponseType> {
    return this.http.put<IIdea>(this.resourceUrl, idea, { observe: 'response' });
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

  generateWriting(number: number, constraint: IWriting): Observable<IWriting[]> {
    let httpParams = new HttpParams();
    httpParams = httpParams.append('number', number.toString());
    return this.http.post<IWriting[]>(`${this.resourceUrl}/writing_options`, constraint, { params: httpParams });
  }

  generateLocation(number: number, constraint: ILocation): Observable<ILocation[]> {
    let httpParams = new HttpParams();
    httpParams = httpParams.append('number', number.toString());
    return this.http.post<ILocation[]>(`${this.resourceUrl}/locations`, constraint, { params: httpParams });
  }

  generatePersona(number: number, constraint: IPersona): Observable<IPersona[]> {
    let httpParams = new HttpParams();
    httpParams = httpParams.append('number', number.toString());
    return this.http.post<IPersona[]>(`${this.resourceUrl}/personas`, constraint, { params: httpParams });
  }

  generateObject(number: number, constraint: IObject): Observable<IObject[]> {
    let httpParams = new HttpParams();
    httpParams = httpParams.append('number', number.toString());
    return this.http.post<IObject[]>(`${this.resourceUrl}/objects`, constraint, { params: httpParams });
  }
}
