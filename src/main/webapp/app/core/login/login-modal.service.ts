import { Injectable } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { LoginModalComponent } from 'app/shared/login/login.component';

@Injectable({ providedIn: 'root' })
export class LoginModalService {
  private isOpen = false;
  private modalRef!: NgbModalRef;

  constructor(private modalService: NgbModal) {}

  open(callback?: () => void): NgbModalRef {
    if (this.isOpen) {
      return this.modalRef;
    }
    this.isOpen = true;
    this.modalRef = this.modalService.open(LoginModalComponent);
    this.modalRef.result
      .then(callback)
      .finally(() => (this.isOpen = false))
      .catch(() => {});
    return this.modalRef;
  }
}
