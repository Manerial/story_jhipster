import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IScene, NewScene } from '../scene.model';

export type PartialUpdateScene = Partial<IScene> & Pick<IScene, 'id'>;

type RestOf<T extends IScene | NewScene> = Omit<T, 'timestampStart'> & {
  timestampStart?: string | null;
};

export type RestScene = RestOf<IScene>;

export type NewRestScene = RestOf<NewScene>;

export type PartialUpdateRestScene = RestOf<PartialUpdateScene>;

export type EntityResponseType = HttpResponse<IScene>;
export type EntityArrayResponseType = HttpResponse<IScene[]>;

@Injectable({ providedIn: 'root' })
export class SceneService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/scenes');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(scene: NewScene): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(scene);
    return this.http.post<RestScene>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(scene: IScene): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(scene);
    return this.http
      .put<RestScene>(`${this.resourceUrl}/${this.getSceneIdentifier(scene)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(scene: PartialUpdateScene): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(scene);
    return this.http
      .patch<RestScene>(`${this.resourceUrl}/${this.getSceneIdentifier(scene)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestScene>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestScene[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSceneIdentifier(scene: Pick<IScene, 'id'>): number {
    return scene.id;
  }

  compareScene(o1: Pick<IScene, 'id'> | null, o2: Pick<IScene, 'id'> | null): boolean {
    return o1 && o2 ? this.getSceneIdentifier(o1) === this.getSceneIdentifier(o2) : o1 === o2;
  }

  addSceneToCollectionIfMissing<Type extends Pick<IScene, 'id'>>(
    sceneCollection: Type[],
    ...scenesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const scenes: Type[] = scenesToCheck.filter(isPresent);
    if (scenes.length > 0) {
      const sceneCollectionIdentifiers = sceneCollection.map(sceneItem => this.getSceneIdentifier(sceneItem)!);
      const scenesToAdd = scenes.filter(sceneItem => {
        const sceneIdentifier = this.getSceneIdentifier(sceneItem);
        if (sceneCollectionIdentifiers.includes(sceneIdentifier)) {
          return false;
        }
        sceneCollectionIdentifiers.push(sceneIdentifier);
        return true;
      });
      return [...scenesToAdd, ...sceneCollection];
    }
    return sceneCollection;
  }

  protected convertDateFromClient<T extends IScene | NewScene | PartialUpdateScene>(scene: T): RestOf<T> {
    return {
      ...scene,
      timestampStart: scene.timestampStart?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restScene: RestScene): IScene {
    return {
      ...restScene,
      timestampStart: restScene.timestampStart ? dayjs(restScene.timestampStart) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestScene>): HttpResponse<IScene> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestScene[]>): HttpResponse<IScene[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
