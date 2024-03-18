import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NidAuxHistoiresSharedModule } from 'app/shared/shared.module';
import { SceneComponent } from './scene.component';
import { SceneDetailComponent } from './scene-detail.component';
import { SceneUpdateComponent } from './scene-update.component';
import { SceneDeleteDialogComponent } from './scene-delete-dialog.component';
import { sceneRoute } from './scene.route';

@NgModule({
  imports: [NidAuxHistoiresSharedModule, RouterModule.forChild(sceneRoute)],
  declarations: [SceneComponent, SceneDetailComponent, SceneUpdateComponent, SceneDeleteDialogComponent],
  entryComponents: [SceneDeleteDialogComponent],
})
export class NidAuxHistoiresSceneModule {}
