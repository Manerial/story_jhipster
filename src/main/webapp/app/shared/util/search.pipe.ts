import { Pipe, PipeTransform } from '@angular/core';
import { User } from 'app/core/user/user.model';
import { Book } from '../model/book.model';

export enum fields {
  LOGIN,
  BOOKS,
}

@Pipe({
  name: 'bookFilter',
})
export class BookFilterPipe implements PipeTransform {
  transform(books: Book[], args: string): any {
    if (args === undefined) {
      return true;
    }

    return books.filter(book => {
      const search = args.toLowerCase();
      return this.searchField(book.name, search) || this.searchField(book.authorLogin, search);
    });
  }

  private searchField(field: undefined | string, search: string): boolean {
    return field !== undefined && field.toLowerCase().includes(search);
  }
}

@Pipe({
  name: 'authorFilter',
})
export class AuthorFilterPipe implements PipeTransform {
  transform(users: User[], search: string, field: fields, ascending: boolean): any {
    if (search === undefined) {
      return true;
    }

    if (field === fields.LOGIN) {
      if (ascending) {
        users.sort((a, b) => a.login.localeCompare(b.login));
      } else {
        users.sort((a, b) => b.login.localeCompare(a.login));
      }
    } else if (field === fields.BOOKS) {
      if (ascending) {
        users.sort((a, b) => a.books - b.books);
      } else {
        users.sort((a, b) => b.books - a.books);
      }
    }

    return users.filter(user => {
      const searchLC = search.toLowerCase();
      return this.searchField(user.login, searchLC);
    });
  }

  private searchField(field: undefined | string, search: string): boolean {
    return field !== undefined && field.toLowerCase().includes(search);
  }
}
