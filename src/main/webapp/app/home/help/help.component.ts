import { Component } from '@angular/core';
import { DISCORD_URL } from 'app/shared/constants/external-links.constants';

@Component({
  selector: 'jhi-help',
  templateUrl: './help.component.html',
})
export class HelpComponent {
  public discordUrl = DISCORD_URL;
}
