import { Pipe, PipeTransform } from '@angular/core';
import { Book } from '../model/book.model';

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
      return this.searchField(book.name, search) || this.searchField(book.author, search);
    });
  }

  private searchField(field: undefined | string, search: string): boolean {
    return field !== undefined && field.toLowerCase().includes(search);
  }
}
