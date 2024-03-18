import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IScene } from '../scene.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../scene.test-samples';

import { SceneService, RestScene } from './scene.service';

const requireRestSample: RestScene = {
  ...sampleWithRequiredData,
  timestampStart: sampleWithRequiredData.timestampStart?.format(DATE_FORMAT),
};

describe('Scene Service', () => {
  let service: SceneService;
  let httpMock: HttpTestingController;
  let expectedResult: IScene | IScene[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SceneService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Scene', () => {
      const scene = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(scene).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Scene', () => {
      const scene = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(scene).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Scene', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Scene', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Scene', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSceneToCollectionIfMissing', () => {
      it('should add a Scene to an empty array', () => {
        const scene: IScene = sampleWithRequiredData;
        expectedResult = service.addSceneToCollectionIfMissing([], scene);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(scene);
      });

      it('should not add a Scene to an array that contains it', () => {
        const scene: IScene = sampleWithRequiredData;
        const sceneCollection: IScene[] = [
          {
            ...scene,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSceneToCollectionIfMissing(sceneCollection, scene);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Scene to an array that doesn't contain it", () => {
        const scene: IScene = sampleWithRequiredData;
        const sceneCollection: IScene[] = [sampleWithPartialData];
        expectedResult = service.addSceneToCollectionIfMissing(sceneCollection, scene);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(scene);
      });

      it('should add only unique Scene to an array', () => {
        const sceneArray: IScene[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const sceneCollection: IScene[] = [sampleWithRequiredData];
        expectedResult = service.addSceneToCollectionIfMissing(sceneCollection, ...sceneArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const scene: IScene = sampleWithRequiredData;
        const scene2: IScene = sampleWithPartialData;
        expectedResult = service.addSceneToCollectionIfMissing([], scene, scene2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(scene);
        expect(expectedResult).toContain(scene2);
      });

      it('should accept null and undefined values', () => {
        const scene: IScene = sampleWithRequiredData;
        expectedResult = service.addSceneToCollectionIfMissing([], null, scene, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(scene);
      });

      it('should return initial array if no Scene is added', () => {
        const sceneCollection: IScene[] = [sampleWithRequiredData];
        expectedResult = service.addSceneToCollectionIfMissing(sceneCollection, undefined, null);
        expect(expectedResult).toEqual(sceneCollection);
      });
    });

    describe('compareScene', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareScene(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareScene(entity1, entity2);
        const compareResult2 = service.compareScene(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareScene(entity1, entity2);
        const compareResult2 = service.compareScene(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareScene(entity1, entity2);
        const compareResult2 = service.compareScene(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
