import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IdeaDetailComponent } from './idea-detail.component';

describe('Idea Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IdeaDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: IdeaDetailComponent,
              resolve: { idea: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(IdeaDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load idea on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', IdeaDetailComponent);

      // THEN
      expect(instance.idea).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
