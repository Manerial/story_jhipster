import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NidAuxHistoiresTestModule } from '../../../test.module';
import { BookStatusDetailComponent } from 'app/entities/bookStatus/bookStatus-detail.component';
import { BookStatus } from 'app/shared/model/bookStatus.model';

describe('Component Tests', () => {
  describe('BookStatus Management Detail Component', () => {
    let comp: BookStatusDetailComponent;
    let fixture: ComponentFixture<BookStatusDetailComponent>;
    const route = ({ data: of({ bookStatus: new BookStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NidAuxHistoiresTestModule],
        declarations: [BookStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BookStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BookStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bookStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bookStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
