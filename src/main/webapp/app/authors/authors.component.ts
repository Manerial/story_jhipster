import { Component, OnInit } from '@angular/core';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { fields } from 'app/shared/util/search.pipe';
import { NavbarService } from 'app/shared/util/search.service';

export enum icons {
  UP,
  DOWN,
  BOTH,
}

@Component({
  selector: 'jhi-authors',
  templateUrl: './authors.component.html',
})
export class AuthorsComponent implements OnInit {
  searchText = '';
  authors: IUser[] = [];
  fieldToSort = fields.LOGIN;
  ascending = true;

  constructor(private navbarService: NavbarService, private userService: UserService) {}

  ngOnInit(): void {
    this.userService.findAuthors().subscribe(authors => {
      this.authors = authors;
    });
    this.navbarService.getCurrentSearch().subscribe(search => {
      this.searchText = search;
    });
  }

  sort(field: fields): void {
    if (this.fieldToSort !== field) {
      this.ascending = true;
      this.fieldToSort = field;
    } else {
      this.ascending = !this.ascending;
    }
  }

  showIcon(field: fields, icon: icons): boolean {
    return (
      (this.fieldToSort !== field && icon === icons.BOTH) ||
      (this.fieldToSort === field && this.ascending === true && icon === icons.UP) ||
      (this.fieldToSort === field && this.ascending === false && icon === icons.DOWN)
    );
  }

  public get fields(): typeof fields {
    return fields;
  }

  public get icons(): typeof icons {
    return icons;
  }
}
