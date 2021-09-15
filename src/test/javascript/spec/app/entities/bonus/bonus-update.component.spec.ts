import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { NidAuxHistoiresTestModule } from '../../../test.module';
import { BonusUpdateComponent } from 'app/entities/bonus/bonus-update.component';
import { BonusService } from 'app/entities/bonus/bonus.service';
import { Bonus } from 'app/shared/model/bonus.model';
import { Account } from 'app/core/user/account.model';

describe('Component Tests', () => {
  describe('Bonus Management Update Component', () => {
    let comp: BonusUpdateComponent;
    let fixture: ComponentFixture<BonusUpdateComponent>;
    let service: BonusService;
    const account: Account = {
      id: 0,
      firstName: 'John',
      lastName: 'Doe',
      activated: true,
      email: 'john.doe@mail.com',
      langKey: 'fr',
      login: 'john',
      authorities: [],
      imageUrl: '',
      introduction: '',
    };

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NidAuxHistoiresTestModule],
        declarations: [BonusUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BonusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BonusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BonusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bonus(123);
        comp.account = account;
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bonus();
        comp.account = account;
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
