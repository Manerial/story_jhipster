import { Component, OnInit } from '@angular/core';
import { IdeaService } from 'app/entities/idea/idea.service';
import { IPersona, Persona } from 'app/shared/model/idea-generator/persona.model';
import { IWriting, Writing } from 'app/shared/model/idea-generator/writing.model';
import { ILocation, Location } from 'app/shared/model/idea-generator/location.model';
import { IObject, Object } from 'app/shared/model/idea-generator/object.model';
import { FormBuilder, Validators } from '@angular/forms';
import { InputPattern } from 'app/shared/util/input-pattern';

@Component({
  selector: 'jhi-idea-generator',
  templateUrl: './idea-generator.component.html',
  styleUrls: ['./idea-generator.component.scss'],
})
export class IdeaGeneratorComponent implements OnInit {
  public writingConstraint: IWriting = new Writing();
  public personaConstraint: IPersona = new Persona();
  public locationConstraint: ILocation = new Location();
  public objectConstraint: IObject = new Object();
  public badTraitsConstraint = '';
  public goodTraitsConstraint = '';
  public handicapsConstraint = '';
  public caracteristicsConstraint = '';
  public writingList: IWriting[] = [];
  public personaList: IPersona[] = [];
  public locationList: ILocation[] = [];
  public objectList: IObject[] = [];
  public hideConstraint = true;
  ideaForm = this.fb.group({
    generationTool: ['persona', [Validators.required]],
    generationNumber: [9, [Validators.required, Validators.min(1), Validators.max(12)]],
  });

  constructor(public ideaService: IdeaService, private fb: FormBuilder, private inputPattern: InputPattern) {}

  ngOnInit(): void {
    this.resetConstraint();
  }

  generate(): void {
    if (!this.ideaForm.valid) {
      return;
    }
    const number = this.ideaForm.get(['generationNumber'])!.value;
    const type = this.ideaForm.get(['generationTool'])!.value;

    this.hideConstraint = true;
    if (type === 'persona') {
      this.turnConstraintTraitsToList();
      this.ideaService.generatePersona(number, this.personaConstraint).subscribe(response => {
        this.personaList = response;
      });
    } else if (type === 'writing_option') {
      this.ideaService.generateWriting(number, this.writingConstraint).subscribe(response => {
        this.writingList = response;
      });
    } else if (type === 'location') {
      this.ideaService.generateLocation(number, this.locationConstraint).subscribe(response => {
        this.locationList = response;
      });
    } else if (type === 'object') {
      this.ideaService.generateObject(number, this.objectConstraint).subscribe(response => {
        this.objectList = response;
      });
    }
  }

  resetConstraint(): void {
    this.writingConstraint = new Writing();
    this.personaConstraint = new Persona();
    this.locationConstraint = new Location();
    this.objectConstraint = new Object();
  }

  turnConstraintTraitsToList(): void {
    if (!this.personaConstraint.traits) throw 'no traits found';
    this.personaConstraint.traits.badTraits = this.badTraitsConstraint.length > 0 ? this.badTraitsConstraint.split(', ') : [];
    this.personaConstraint.traits.goodTraits = this.goodTraitsConstraint.length > 0 ? this.goodTraitsConstraint.split(', ') : [];
    this.personaConstraint.traits.caracteristics =
      this.caracteristicsConstraint.length > 0 ? this.caracteristicsConstraint.split(', ') : [];
    this.personaConstraint.traits.handicaps = this.handicapsConstraint.length > 0 ? this.handicapsConstraint.split(', ') : [];
  }

  _keyNumber(event: any): void {
    this.inputPattern.forceNumber(event);
  }
}
