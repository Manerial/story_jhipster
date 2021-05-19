import { Component, OnInit } from '@angular/core';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-authors',
  templateUrl: './authors.component.html',
})
export class AuthorsComponent implements OnInit {
  authors: IUser[] = [];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.findAuthors().subscribe(authors => (this.authors = authors));
  }
}
