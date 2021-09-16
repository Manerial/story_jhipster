import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

@Component({
  selector: 'jhi-account',
  templateUrl: './account.component.html',
})
export class AccountComponent implements OnInit {
  account!: Account;

  constructor(private accountService: AccountService, protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.account = account;
      }
    });
  }
}
