import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
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
    const copy = this.convertDateFromClient(scene);
    return this.http
      .post<IScene>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(scene: IScene): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(scene);
    return this.http
      .put<IScene>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IScene>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IScene[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(scene: IScene): IScene {
    const copy: IScene = Object.assign({}, scene, {
      timestampStart: scene.timestampStart && scene.timestampStart.isValid() ? scene.timestampStart.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.timestampStart = res.body.timestampStart ? moment(res.body.timestampStart) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((scene: IScene) => {
        scene.timestampStart = scene.timestampStart ? moment(scene.timestampStart) : undefined;
      });
    }
    return res;
  }
}
