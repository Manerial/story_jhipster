import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { NidAuxHistoiresTestModule } from '../../../test.module';
import { BookComponent } from 'app/entities/book/book.component';
import { BookService } from 'app/entities/book/book.service';
import { Book } from 'app/shared/model/book.model';
import { Account } from 'app/core/user/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { MockAccountService } from '../../../helpers/mock-account.service';

describe('Component Tests', () => {
  describe('Book Management Component', () => {
    let comp: BookComponent;
    let fixture: ComponentFixture<BookComponent>;
    let service: BookService;
    let mockAccount: MockAccountService;
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
        declarations: [BookComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(BookComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookService);
      mockAccount = TestBed.get(AccountService);
      mockAccount.setIdentityResponse(account);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'findAllByAuthor').and.returnValue(
        of(
          new HttpResponse({
            body: [new Book(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.findAllByAuthor).toHaveBeenCalled();
      expect(comp.books && comp.books[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'findAllByAuthor').and.returnValue(
        of(
          new HttpResponse({
            body: [new Book(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.findAllByAuthor).toHaveBeenCalled();
      expect(comp.books && comp.books[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
