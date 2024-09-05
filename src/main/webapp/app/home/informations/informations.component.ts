import { Component } from '@angular/core';
import { PROPRIETE_INTELECTUELLE_LEGIFRANCE_URL } from 'app/shared/constants/external-links.constants';

@Component({
  selector: 'jhi-informations',
  templateUrl: './informations.component.html',
})
export class InformationsComponent {
  public PILegifranceUrl = PROPRIETE_INTELECTUELLE_LEGIFRANCE_URL;
}
