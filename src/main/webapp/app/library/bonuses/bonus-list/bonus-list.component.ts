import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BonusService } from 'app/entities/bonus/bonus.service';
import { BookService } from 'app/entities/book/book.service';
import { IBonus } from 'app/shared/model/bonus.model';
import { TitleService } from 'app/shared/util/title.service';

@Component({
  selector: 'jhi-bonus-list',
  templateUrl: './bonus-list.component.html',
  styleUrls: ['./bonus-list.component.scss'],
})
export class BonusListComponent implements OnInit {
  public bonusList: IBonus[] = [];
  public bonusIsLoading = true;
  public entityName = '';
  public showDescription: boolean[] = [];

  constructor(
    public acRoute: ActivatedRoute,
    private bookService: BookService,
    private bonusService: BonusService,
    private titleService: TitleService
  ) {}

  ngOnInit(): void {
    this.acRoute.paramMap.subscribe(params => {
      const bookIdStr = Number(params.get('bookId'));
      this.bookService.findLight(bookIdStr).subscribe(entity => {
        if (entity.body) {
          this.entityName = entity.body.name!;
          this.titleService.replaceTitle('bonus.title', { entityName: this.entityName });
        }

        this.getAllBonusByBookId(bookIdStr);
      });
    });
  }

  getAllBonusByBookId(id: number): void {
    this.bonusIsLoading = true;
    this.bonusService.getAllBonusByBookId(id).subscribe(bonusList => {
      if (bonusList.body) {
        this.bonusList = bonusList.body;
        for (const bonus of this.bonusList) {
          this.showDescription[bonus.id!] = false;
        }
      }
      this.bonusIsLoading = false;
    });
  }

  downloadBonus(bonus: IBonus): void {
    this.bonusService.download(bonus.id!).subscribe(data => {
      if (data.body != null) {
        const downloadURL = window.URL.createObjectURL(data.body);
        const anchor = document.createElement('a');
        anchor.download = bonus.name + '.' + bonus.dataContentType;
        anchor.href = downloadURL;
        anchor.click();
      }
    });
  }

  toggleDescription(bonusId: any): void {
    this.showDescription[bonusId] = !this.showDescription[bonusId];
  }
}
