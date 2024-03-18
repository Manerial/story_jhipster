import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ChapterDetailComponent } from './chapter-detail.component';

describe('Chapter Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChapterDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ChapterDetailComponent,
              resolve: { chapter: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ChapterDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load chapter on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ChapterDetailComponent);

      // THEN
      expect(instance.chapter).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
