import { Component, OnInit } from '@angular/core';
import { IdeaService } from 'app/entities/idea/idea.service';
import { IPersona, Persona } from 'app/shared/model/idea-generator/persona.model';
import { IWriting, Writing } from 'app/shared/model/idea-generator/writing.model';

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
  public badTraitsConstraint = '';
  public goodTraitsConstraint = '';
  public handicapsConstraint = '';
  public caracteristicsConstraint = '';
  public writingList: IWriting[] = [];
  public personaList: IPersona[] = [];
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
    } else {
      this.ideaService.generateWriting(this.number, this.writingConstraint).subscribe(response => {
        this.writingList = response;
      });
    }
  }

  resetConstraint(): void {
    this.writingConstraint = new Writing();
    this.personaConstraint = new Persona();
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
