import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IScene } from 'app/shared/model/scene.model';
import { SceneService } from './scene.service';

@Component({
  templateUrl: './scene-delete-dialog.component.html',
})
export class SceneDeleteDialogComponent {
  scene?: IScene;

  constructor(protected sceneService: SceneService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sceneService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sceneListModification');
      this.activeModal.close();
    });
  }
}
