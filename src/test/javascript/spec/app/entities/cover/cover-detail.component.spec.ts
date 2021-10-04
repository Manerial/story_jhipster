import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { NidAuxHistoiresTestModule } from '../../../test.module';
import { CoverDetailComponent } from 'app/entities/cover/cover-detail.component';
import { Cover } from 'app/shared/model/cover.model';

describe('Component Tests', () => {
  describe('Cover Management Detail Component', () => {
    let comp: CoverDetailComponent;
    let fixture: ComponentFixture<CoverDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ cover: new Cover(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NidAuxHistoiresTestModule],
        declarations: [CoverDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CoverDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CoverDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load cover on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cover).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
