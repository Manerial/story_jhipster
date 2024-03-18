import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IWordAnalysis } from '../word-analysis.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../word-analysis.test-samples';

import { WordAnalysisService } from './word-analysis.service';

const requireRestSample: IWordAnalysis = {
  ...sampleWithRequiredData,
};

describe('WordAnalysis Service', () => {
  let service: WordAnalysisService;
  let httpMock: HttpTestingController;
  let expectedResult: IWordAnalysis | IWordAnalysis[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(WordAnalysisService);
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

    it('should create a WordAnalysis', () => {
      const wordAnalysis = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(wordAnalysis).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a WordAnalysis', () => {
      const wordAnalysis = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(wordAnalysis).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a WordAnalysis', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of WordAnalysis', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a WordAnalysis', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addWordAnalysisToCollectionIfMissing', () => {
      it('should add a WordAnalysis to an empty array', () => {
        const wordAnalysis: IWordAnalysis = sampleWithRequiredData;
        expectedResult = service.addWordAnalysisToCollectionIfMissing([], wordAnalysis);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(wordAnalysis);
      });

      it('should not add a WordAnalysis to an array that contains it', () => {
        const wordAnalysis: IWordAnalysis = sampleWithRequiredData;
        const wordAnalysisCollection: IWordAnalysis[] = [
          {
            ...wordAnalysis,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addWordAnalysisToCollectionIfMissing(wordAnalysisCollection, wordAnalysis);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a WordAnalysis to an array that doesn't contain it", () => {
        const wordAnalysis: IWordAnalysis = sampleWithRequiredData;
        const wordAnalysisCollection: IWordAnalysis[] = [sampleWithPartialData];
        expectedResult = service.addWordAnalysisToCollectionIfMissing(wordAnalysisCollection, wordAnalysis);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(wordAnalysis);
      });

      it('should add only unique WordAnalysis to an array', () => {
        const wordAnalysisArray: IWordAnalysis[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const wordAnalysisCollection: IWordAnalysis[] = [sampleWithRequiredData];
        expectedResult = service.addWordAnalysisToCollectionIfMissing(wordAnalysisCollection, ...wordAnalysisArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const wordAnalysis: IWordAnalysis = sampleWithRequiredData;
        const wordAnalysis2: IWordAnalysis = sampleWithPartialData;
        expectedResult = service.addWordAnalysisToCollectionIfMissing([], wordAnalysis, wordAnalysis2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(wordAnalysis);
        expect(expectedResult).toContain(wordAnalysis2);
      });

      it('should accept null and undefined values', () => {
        const wordAnalysis: IWordAnalysis = sampleWithRequiredData;
        expectedResult = service.addWordAnalysisToCollectionIfMissing([], null, wordAnalysis, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(wordAnalysis);
      });

      it('should return initial array if no WordAnalysis is added', () => {
        const wordAnalysisCollection: IWordAnalysis[] = [sampleWithRequiredData];
        expectedResult = service.addWordAnalysisToCollectionIfMissing(wordAnalysisCollection, undefined, null);
        expect(expectedResult).toEqual(wordAnalysisCollection);
      });
    });

    describe('compareWordAnalysis', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareWordAnalysis(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareWordAnalysis(entity1, entity2);
        const compareResult2 = service.compareWordAnalysis(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareWordAnalysis(entity1, entity2);
        const compareResult2 = service.compareWordAnalysis(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareWordAnalysis(entity1, entity2);
        const compareResult2 = service.compareWordAnalysis(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
