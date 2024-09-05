import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NidAuxHistoiresTestModule } from '../../../test.module';
import { BonusDetailComponent } from 'app/entities/bonus/bonus-detail.component';
import { Bonus } from 'app/shared/model/bonus.model';

describe('Component Tests', () => {
  describe('Bonus Management Detail Component', () => {
    let comp: BonusDetailComponent;
    let fixture: ComponentFixture<BonusDetailComponent>;
    const route = ({ data: of({ bonus: new Bonus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NidAuxHistoiresTestModule],
        declarations: [BonusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BonusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BonusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bonus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bonus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
