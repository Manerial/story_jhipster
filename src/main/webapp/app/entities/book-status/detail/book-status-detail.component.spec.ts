import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { BookStatusDetailComponent } from './book-status-detail.component';

describe('BookStatus Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookStatusDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: BookStatusDetailComponent,
              resolve: { bookStatus: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(BookStatusDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load bookStatus on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', BookStatusDetailComponent);

      // THEN
      expect(instance.bookStatus).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
