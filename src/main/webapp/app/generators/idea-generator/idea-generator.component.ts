import { Component, OnInit } from '@angular/core';
import { IPersona, Persona } from 'app/shared/model/idea-generator/persona.model';
import { IWritingOption, WritingOption } from 'app/shared/model/idea-generator/writing-option.model';
import { ILocation, Location } from 'app/shared/model/idea-generator/location.model';
import { IObject, Object } from 'app/shared/model/idea-generator/object.model';
import { FormBuilder, Validators } from '@angular/forms';
import { InputPattern } from 'app/shared/util/input-pattern';
import { GeneratorService } from '../generator.service';
import { IHonoraryTitle } from 'app/shared/model/idea-generator/honorary-title.model';

@Component({
  selector: 'jhi-idea-generator',
  templateUrl: './idea-generator.component.html',
  styleUrls: ['./idea-generator.component.scss'],
})
export class IdeaGeneratorComponent implements OnInit {
  public writingOptionConstraint: IWritingOption = new WritingOption();
  public personaConstraint: IPersona = new Persona();
  public locationConstraint: ILocation = new Location();
  public objectConstraint: IObject = new Object();

  public badTraitsConstraint = '';
  public goodTraitsConstraint = '';
  public handicapsConstraint = '';
  public caracteristicsConstraint = '';

  public writingOptionList: IWritingOption[] = [];
  public personaList: IPersona[] = [];
  public locationList: ILocation[] = [];
  public objectList: IObject[] = [];
  public honoraryTitleList: IHonoraryTitle[] = [];

  public hideConstraint = true;
  ideaForm = this.fb.group({
    generationTool: ['persona', [Validators.required]],
    generationNumber: [9, [Validators.required, Validators.min(1), Validators.max(12)]],
  });

  constructor(public generatorService: GeneratorService, private fb: FormBuilder, private inputPattern: InputPattern) {}

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
      this.generatorService.generatePersona(number, this.personaConstraint).subscribe(response => {
        this.personaList = response;
      });
    } else if (type === 'writing_option') {
      this.generatorService.generateWritingOption(number, this.writingOptionConstraint).subscribe(response => {
        this.writingOptionList = response;
      });
    } else if (type === 'location') {
      this.generatorService.generateLocation(number, this.locationConstraint).subscribe(response => {
        this.locationList = response;
      });
    } else if (type === 'object') {
      this.generatorService.generateObject(number, this.objectConstraint).subscribe(response => {
        this.objectList = response;
      });
    } else if (type === 'honorary_title') {
      this.generatorService.generateHonoraryTitle(number).subscribe(response => {
        this.honoraryTitleList = response;
      });
    }
  }

  resetConstraint(): void {
    this.writingOptionConstraint = new WritingOption();
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
