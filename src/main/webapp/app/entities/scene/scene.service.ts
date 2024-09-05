import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IScene } from 'app/shared/model/scene.model';

type EntityResponseType = HttpResponse<IScene>;
type EntityArrayResponseType = HttpResponse<IScene[]>;

@Injectable({ providedIn: 'root' })
export class SceneService {
  public resourceUrl = SERVER_API_URL + 'api/scenes';

  constructor(protected http: HttpClient) {}

  create(scene: IScene): Observable<EntityResponseType> {
    return this.http
      .post<IScene>(this.resourceUrl, scene, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => res));
  }

  update(scene: IScene): Observable<EntityResponseType> {
    return this.http
      .put<IScene>(this.resourceUrl, scene, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => res));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IScene>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => res));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IScene[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => res));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
