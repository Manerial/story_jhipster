import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NidAuxHistoiresTestModule } from '../../../test.module';
import { WordAnalysisDetailComponent } from 'app/entities/word-analysis/word-analysis-detail.component';
import { WordAnalysis } from 'app/shared/model/word-analysis.model';

describe('Component Tests', () => {
  describe('WordAnalysis Management Detail Component', () => {
    let comp: WordAnalysisDetailComponent;
    let fixture: ComponentFixture<WordAnalysisDetailComponent>;
    const route = ({ data: of({ wordAnalysis: new WordAnalysis(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NidAuxHistoiresTestModule],
        declarations: [WordAnalysisDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WordAnalysisDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WordAnalysisDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load wordAnalysis on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.wordAnalysis).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
