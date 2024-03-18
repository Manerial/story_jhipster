import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IScene } from '../scene.model';
import { SceneService } from '../service/scene.service';

@Component({
  standalone: true,
  templateUrl: './scene-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SceneDeleteDialogComponent {
  scene?: IScene;

  constructor(
    protected sceneService: SceneService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sceneService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
