import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { WordAnalysisDetailComponent } from './word-analysis-detail.component';

describe('WordAnalysis Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WordAnalysisDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: WordAnalysisDetailComponent,
              resolve: { wordAnalysis: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(WordAnalysisDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load wordAnalysis on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', WordAnalysisDetailComponent);

      // THEN
      expect(instance.wordAnalysis).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
