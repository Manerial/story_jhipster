import { Component, OnInit } from '@angular/core';
import { IdeaService } from 'app/entities/idea/idea.service';
import { IPersona, Persona } from 'app/shared/model/idea-generator/persona.model';
import { IWriting, Writing } from 'app/shared/model/idea-generator/writing.model';
import { ILocation, Location } from 'app/shared/model/idea-generator/location.model';

@Component({
  selector: 'jhi-idea-generator',
  templateUrl: './idea-generator.component.html',
  styleUrls: ['./idea-generator.component.scss'],
})
export class IdeaGeneratorComponent implements OnInit {
  public type = 'persona';
  public number = 9;
  public writingConstraint: IWriting = new Writing();
  public personaConstraint: IPersona = new Persona();
  public locationConstraint: ILocation = new Location();
  public badTraitsConstraint = '';
  public goodTraitsConstraint = '';
  public handicapsConstraint = '';
  public caracteristicsConstraint = '';
  public writingList: IWriting[] = [];
  public personaList: IPersona[] = [];
  public locationList: ILocation[] = [];
  public hideConstraint = true;

  constructor(public ideaService: IdeaService) {}

  ngOnInit(): void {
    this.resetConstraint();
  }

  generate(): void {
    this.hideConstraint = true;
    if (this.type === 'persona') {
      this.turnConstraintTraitsToList();
      this.ideaService.generatePersona(this.number, this.personaConstraint).subscribe(response => {
        this.personaList = response;
      });
    } else if (this.type === 'writing_option') {
      this.ideaService.generateWriting(this.number, this.writingConstraint).subscribe(response => {
        this.writingList = response;
      });
    } else if (this.type === 'location') {
      this.ideaService.generateLocation(this.number, this.locationConstraint).subscribe(response => {
        this.locationList = response;
      });
    }
  }

  resetConstraint(): void {
    this.writingConstraint = new Writing();
    this.personaConstraint = new Persona();
    this.locationConstraint = new Location();
  }

  turnConstraintTraitsToList(): void {
    if (!this.personaConstraint.traits) throw 'no traits found';
    this.personaConstraint.traits.badTraits = this.badTraitsConstraint.length > 0 ? this.badTraitsConstraint.split(', ') : [];
    this.personaConstraint.traits.goodTraits = this.goodTraitsConstraint.length > 0 ? this.goodTraitsConstraint.split(', ') : [];
    this.personaConstraint.traits.caracteristics =
      this.caracteristicsConstraint.length > 0 ? this.caracteristicsConstraint.split(', ') : [];
    this.personaConstraint.traits.handicaps = this.handicapsConstraint.length > 0 ? this.handicapsConstraint.split(', ') : [];
  }
}
