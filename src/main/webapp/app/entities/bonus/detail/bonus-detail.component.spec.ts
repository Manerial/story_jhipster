import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { BonusDetailComponent } from './bonus-detail.component';

describe('Bonus Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BonusDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: BonusDetailComponent,
              resolve: { bonus: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(BonusDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load bonus on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', BonusDetailComponent);

      // THEN
      expect(instance.bonus).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
