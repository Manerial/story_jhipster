import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IWritingOption } from 'app/shared/model/idea-generator/writing-option.model';
import { IPersona } from 'app/shared/model/idea-generator/persona.model';
import { ILocation } from 'app/shared/model/idea-generator/location.model';
import { IObject } from 'app/shared/model/idea-generator/object.model';
import { IType } from 'app/shared/model/type.model';
import { IHonoraryTitle } from 'app/shared/model/idea-generator/honorary-title.model';
import { ICreature } from 'app/shared/model/idea-generator/creature.model';
import { IQuest } from 'app/shared/model/idea-generator/quest.model';

type ITypeArrayResponseType = HttpResponse<IType[]>;

@Injectable({ providedIn: 'root' })
export class GeneratorService {
  public resourceUrl = SERVER_API_URL + 'api/generate';

  constructor(protected http: HttpClient) {}

  generateWritingOption(number: number, constraint: IWritingOption): Observable<IWritingOption[]> {
    let httpParams = new HttpParams();
    httpParams = httpParams.append('number', number.toString());
    return this.http.post<IWritingOption[]>(`${this.resourceUrl}/writing_options`, constraint, { params: httpParams });
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

  generateCreature(number: number, constraint: ICreature): Observable<ICreature[]> {
    let httpParams = new HttpParams();
    httpParams = httpParams.append('number', number.toString());
    return this.http.post<ICreature[]>(`${this.resourceUrl}/creatures`, constraint, { params: httpParams });
  }

  generateHonoraryTitle(number: number): Observable<IHonoraryTitle[]> {
    let httpParams = new HttpParams();
    httpParams = httpParams.append('number', number.toString());
    return this.http.post<IHonoraryTitle[]>(`${this.resourceUrl}/honorary_titles`, null, { params: httpParams });
  }

  generateObject(number: number, constraint: IObject): Observable<IObject[]> {
    let httpParams = new HttpParams();
    httpParams = httpParams.append('number', number.toString());
    return this.http.post<IObject[]>(`${this.resourceUrl}/objects`, constraint, { params: httpParams });
  }

  generateQuest(number: any, constraint: IQuest): Observable<IQuest[]> {
    let httpParams = new HttpParams();
    httpParams = httpParams.append('number', number.toString());
    return this.http.post<IQuest[]>(`${this.resourceUrl}/quests`, constraint, { params: httpParams });
  }

  getTypes(): Observable<ITypeArrayResponseType> {
    return this.http.get<IType[]>(`${this.resourceUrl}/get_types`, { observe: 'response' });
  }

  generateWords(number: number, fixLength: number, type: string): Observable<string[]> {
    let httpParams = new HttpParams();
    httpParams = httpParams.append('number', number.toString());
    httpParams = httpParams.append('fixLength', fixLength.toString());
    httpParams = httpParams.append('type', type);

    return this.http.get<string[]>(`${this.resourceUrl}/words`, { params: httpParams });
  }
}
