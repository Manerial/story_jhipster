import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBonus } from 'app/shared/model/bonus.model';

type EntityResponseType = HttpResponse<IBonus>;
type EntityArrayResponseType = HttpResponse<IBonus[]>;
type BonusResponseType = HttpResponse<Blob>;

@Injectable({ providedIn: 'root' })
export class BonusService {
  public resourceUrl = SERVER_API_URL + 'api/bonuses';
  public downloadUrl = SERVER_API_URL + 'api/download';

  constructor(protected http: HttpClient) {}

  create(bonus: IBonus): Observable<EntityResponseType> {
    return this.http.post<IBonus>(this.resourceUrl, bonus, { observe: 'response' });
  }

  update(bonus: IBonus): Observable<EntityResponseType> {
    return this.http.put<IBonus>(this.resourceUrl, bonus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBonus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBonus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAllBonusByBookId(id: number): Observable<EntityArrayResponseType> {
    return this.http.get<IBonus[]>(`${this.resourceUrl}/book/${id}`, { observe: 'response' });
  }

  download(id: number): Observable<BonusResponseType> {
    return this.http.get(`${this.downloadUrl}/bonus/${id}`, { observe: 'response', responseType: 'blob' });
  }
}
