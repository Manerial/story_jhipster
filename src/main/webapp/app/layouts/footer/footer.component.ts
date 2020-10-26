import { Component } from '@angular/core';
import { DISCORD_URL, GITHUB_URL } from 'app/shared/constants/external-links.constants';

@Component({
  selector: 'jhi-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.scss', './footer.responsive.scss'],
})
export class FooterComponent {
  public discordUrl = DISCORD_URL;
  public githubUrl = GITHUB_URL;
}
