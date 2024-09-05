import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ImageService } from 'app/entities/image/image.service';
import { ReaderService } from 'app/library/reader/reader.service';
import { IImage } from 'app/shared/model/image.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'jhi-image-list',
  templateUrl: './image-list.component.html',
  styleUrls: ['./image-list.component.scss'],
})
export class ImageListComponent implements OnInit, OnDestroy {
  public imageList: IImage[] = [];
  public isLoading = true;
  private previousChapterId = 0;
  private bookSubscriber: Subscription = new Subscription();
  private partSubscriber: Subscription = new Subscription();
  private chapterSubscriber: Subscription = new Subscription();
  private entitySubscriber: Subscription = new Subscription();
  public entityName = '';

  constructor(public acRoute: ActivatedRoute, private imageService: ImageService, private readerService: ReaderService) {}

  ngOnInit(): void {
    this.bookSubscriber = this.readerService.currentBookIdObs.subscribe(id => {
      if (id !== '') {
        this.getAllImageByEntity('book', parseInt(id, 10));
      }
    });

    this.partSubscriber = this.readerService.currentPartIdObs.subscribe(id => {
      if (id !== '') {
        this.getAllImageByEntity('part', parseInt(id, 10));
      }
    });

    this.chapterSubscriber = this.readerService.currentChapterIdObs.subscribe(id => {
      if (id !== '') {
        if (this.previousChapterId === 0) {
          this.initComponent(id);
        } else {
          this.getAllImageByEntity('chapter', parseInt(id, 10));
        }
      }
    });

    this.entitySubscriber = this.readerService.currentEntityNameObs.subscribe(entityName => {
      this.entityName = entityName;
    });
  }

  ngOnDestroy(): void {
    this.bookSubscriber.unsubscribe();
    this.partSubscriber.unsubscribe();
    this.chapterSubscriber.unsubscribe();
    this.entitySubscriber.unsubscribe();
    this.readerService.changeChapter(this.previousChapterId);
  }

  initComponent(id: string): void {
    this.previousChapterId = parseInt(id, 10);
    this.readerService.book.subscribe(book => {
      this.readerService.changeBook(book.id);
    });
  }

  getAllImageByEntity(entity: string, id: number): void {
    this.isLoading = true;
    this.imageService.getAllImagesByEntityId(entity, id).subscribe(imageList => {
      if (imageList.body) {
        this.imageList = imageList.body;
      }
      this.isLoading = false;
    });
  }
}
