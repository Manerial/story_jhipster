import { Component, OnInit } from '@angular/core';
import { WordAnalysisService } from 'app/entities/word-analysis/word-analysis.service';
import { IType, Type } from 'app/shared/model/type.model';

@Component({
  selector: 'jhi-word-generator',
  templateUrl: './word-generator.component.html',
  styleUrls: ['./word-generator.component.scss'],
})
export class WordGeneratorComponent implements OnInit {
  public words: string[] = [];
  public typeList: IType[] = [];
  public currentType: IType = new Type();
  public numberOfWords = 5;
  public fixLength = 0;

  constructor(public wordGeneratorService: WordAnalysisService) {}

  ngOnInit(): void {
    this.wordGeneratorService.getTypes().subscribe(types => {
      if (!types.body) throw 'No types found';
      this.typeList = types.body;
      this.currentType = this.typeList[0];
    });
  }

  getWords(): void {
    if (this.numberOfWords <= 200 && this.fixLength <= 20) {
      this.words = [];
      this.wordGeneratorService.generateWords(this.numberOfWords, this.fixLength, this.currentType.type).subscribe(words => {
        words.forEach(word => {
          this.words.push(word);
        });
      });
    }
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
}
