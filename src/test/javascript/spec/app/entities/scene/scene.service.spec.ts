import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SceneService } from 'app/entities/scene/scene.service';
import { IScene, Scene } from 'app/shared/model/scene.model';

describe('Service Tests', () => {
  describe('Scene Service', () => {
    let injector: TestBed;
    let service: SceneService;
    let httpMock: HttpTestingController;
    let elemDefault: IScene;
    let expectedResult: IScene | IScene[] | boolean | null;
    let currentDate: Date;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SceneService);
      httpMock = injector.get(HttpTestingController);
      currentDate = new Date();

      elemDefault = new Scene(0, 'AAAAAAA', 0, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            timestampStart: currentDate,
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Scene', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            timestampStart: currentDate,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            timestampStart: currentDate,
          },
          returnedFromService
        );

        service.create(new Scene()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Scene', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            number: 1,
            text: 'BBBBBB',
            timestampStart: currentDate,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            timestampStart: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Scene', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            number: 1,
            text: 'BBBBBB',
            timestampStart: currentDate,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            timestampStart: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Scene', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
