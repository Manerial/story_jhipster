import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BonusService } from '../service/bonus.service';
import { IBonus } from '../bonus.model';
import { BonusFormService } from './bonus-form.service';

import { BonusUpdateComponent } from './bonus-update.component';

describe('Bonus Management Update Component', () => {
  let comp: BonusUpdateComponent;
  let fixture: ComponentFixture<BonusUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bonusFormService: BonusFormService;
  let bonusService: BonusService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), BonusUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(BonusUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BonusUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bonusFormService = TestBed.inject(BonusFormService);
    bonusService = TestBed.inject(BonusService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const bonus: IBonus = { id: 456 };

      activatedRoute.data = of({ bonus });
      comp.ngOnInit();

      expect(comp.bonus).toEqual(bonus);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBonus>>();
      const bonus = { id: 123 };
      jest.spyOn(bonusFormService, 'getBonus').mockReturnValue(bonus);
      jest.spyOn(bonusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bonus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bonus }));
      saveSubject.complete();

      // THEN
      expect(bonusFormService.getBonus).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(bonusService.update).toHaveBeenCalledWith(expect.objectContaining(bonus));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBonus>>();
      const bonus = { id: 123 };
      jest.spyOn(bonusFormService, 'getBonus').mockReturnValue({ id: null });
      jest.spyOn(bonusService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bonus: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bonus }));
      saveSubject.complete();

      // THEN
      expect(bonusFormService.getBonus).toHaveBeenCalled();
      expect(bonusService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBonus>>();
      const bonus = { id: 123 };
      jest.spyOn(bonusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bonus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bonusService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
