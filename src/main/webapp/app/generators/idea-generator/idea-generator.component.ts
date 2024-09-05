import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { InputPattern } from 'app/shared/util/input-pattern';
import { GeneratorService } from '../generator.service';
import { IPersona, Persona } from 'app/shared/model/idea-generator/persona.model';
import { IWritingOption, WritingOption } from 'app/shared/model/idea-generator/writing-option.model';
import { ILocation, Location } from 'app/shared/model/idea-generator/location.model';
import { IObject, Object } from 'app/shared/model/idea-generator/object.model';
import { IHonoraryTitle } from 'app/shared/model/idea-generator/honorary-title.model';
import { ICreature, Creature } from 'app/shared/model/idea-generator/creature.model';

@Component({
  selector: 'jhi-idea-generator',
  templateUrl: './idea-generator.component.html',
  styleUrls: ['./idea-generator.component.scss'],
})
export class IdeaGeneratorComponent implements OnInit {
  public personaConstraint: IPersona = new Persona();
  public pe_badTraitsConstraint = '';
  public pe_goodTraitsConstraint = '';
  public pe_handicapsConstraint = '';
  public pe_caracteristicsConstraint = '';

  public creatureConstraint: ICreature = new Creature();
  public cr_locationConstraint = '';
  public cr_senseConstraint = '';
  public cr_dietConstraint = '';
  public cr_attributeConstraint = '';
  public cr_skillConstraint = '';

  public writingOptionConstraint: IWritingOption = new WritingOption();
  public locationConstraint: ILocation = new Location();
  public objectConstraint: IObject = new Object();

  public writingOptionList: IWritingOption[] = [];
  public personaList: IPersona[] = [];
  public locationList: ILocation[] = [];
  public objectList: IObject[] = [];
  public honoraryTitleList: IHonoraryTitle[] = [];
  public creatureList: ICreature[] = [];

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
    } else if (type === 'creature') {
      this.turnConstraintCreatureToList();
      this.generatorService.generateCreature(number, this.creatureConstraint).subscribe(response => {
        this.creatureList = response;
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
    this.personaConstraint.traits.badTraits = this.stringTolist(this.pe_badTraitsConstraint);
    this.personaConstraint.traits.goodTraits = this.stringTolist(this.pe_goodTraitsConstraint);
    this.personaConstraint.traits.caracteristics = this.stringTolist(this.pe_caracteristicsConstraint);
    this.personaConstraint.traits.handicaps = this.stringTolist(this.pe_handicapsConstraint);
  }

  turnConstraintCreatureToList(): void {
    this.creatureConstraint.attributes = this.stringTolist(this.cr_attributeConstraint);
    this.creatureConstraint.locations = this.stringTolist(this.cr_locationConstraint);
    this.creatureConstraint.skills = this.stringTolist(this.cr_skillConstraint);
    this.creatureConstraint.diets = this.stringTolist(this.cr_dietConstraint);
  }

  stringTolist(stringList: string): string[] {
    return stringList.length > 0 ? stringList.split(', ') : [];
  }

  _keyNumber(event: any): void {
    this.inputPattern.forceNumber(event);
  }
}
