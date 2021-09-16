import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class NavbarService {
  private search = new BehaviorSubject('');
  public currentSearch = this.search.asObservable();

  private isViewBook = new BehaviorSubject(true);
  public currentIsViewBook = this.isViewBook.asObservable();

  changeSearch(search: string): void {
    this.search.next(search);
  }

  getCurrentSearch(): Observable<string> {
    return this.currentSearch;
  }

  changeIsViewBook(isViewBook: boolean): void {
    this.isViewBook.next(isViewBook);
  }

  getCurrentIsViewBook(): Observable<boolean> {
    return this.currentIsViewBook;
  }
}
