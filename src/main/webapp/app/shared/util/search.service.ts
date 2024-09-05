import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  private search = new BehaviorSubject('');
  public currentSearch = this.search.asObservable();

  constructor() {}

  changeSearch(search: string): void {
    this.search.next(search);
  }

  getCurrentSearch(): Observable<string> {
    return this.currentSearch;
  }
}
