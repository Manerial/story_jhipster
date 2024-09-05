import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { IType } from 'app/shared/model/type.model';
import { InputPattern } from 'app/shared/util/input-pattern';
import { GeneratorService } from '../generator.service';

@Component({
  selector: 'jhi-word-generator',
  templateUrl: './word-generator.component.html',
  styleUrls: ['./word-generator.component.scss'],
})
export class WordGeneratorComponent implements OnInit {
  public words: string[] = [];
  public typeList: IType[] = [];

  wordForm = this.fb.group({
    type: ['', [Validators.required]],
    numberOfWords: [20, [Validators.required, Validators.min(1), Validators.max(200)]],
    fixLength: [0, [Validators.required, Validators.min(0), Validators.max(25)]],
  });

  constructor(public generatorService: GeneratorService, private fb: FormBuilder, private inputPattern: InputPattern) {}

  ngOnInit(): void {
    this.generatorService.getTypes().subscribe(types => {
      if (!types.body) throw 'No types found';
      this.typeList = types.body;
      this.wordForm.controls['type'].setValue(this.typeList[0].type);
    });
  }

  getWords(): void {
    if (!this.wordForm.valid) {
      return;
    }

    const numberOfWords = this.wordForm.get('numberOfWords')!.value;
    const fixLength = this.wordForm.get('fixLength')!.value;
    const type: string = this.wordForm.get('type')!.value;

    this.generatorService.generateWords(numberOfWords, fixLength, type.toUpperCase()).subscribe(words => {
      this.words = [];
      words.forEach(word => {
        this.words.push(word);
      });
    });
  }

  copyToClipboard(val: string): void {
    const selBox = document.createElement('textarea');
    selBox.style.position = 'fixed';
    selBox.style.left = '0';
    selBox.style.top = '0';
    selBox.style.opacity = '0';
    selBox.value = val;
    document.body.appendChild(selBox);
    selBox.focus();
    selBox.select();
    document.execCommand('copy');
    document.body.removeChild(selBox);
  }

  _keyNumber(event: any): void {
    this.inputPattern.forceNumber(event);
  }
}
