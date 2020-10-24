import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ImageService } from 'app/entities/image/image.service';
import { UtilService } from 'app/shared/util/util.service';

@Component({
  selector: 'jhi-image-viewer',
  templateUrl: './image-viewer.component.html',
  styleUrls: ['./image-viewer.component.scss'],
})
export class ImageViewerComponent implements OnInit {
  public isLoading = true;
  public imageId = 0;
  public imageElement = new HTMLImageElement();

  constructor(public acRoute: ActivatedRoute, public imageService: ImageService, private utilService: UtilService) {}

  ngOnInit(): void {
    this.acRoute.paramMap.subscribe(params => {
      this.imageId = this.utilService.getParamNumber(params, 'imageId');

      this.imageService.find(this.imageId).subscribe(response => {
        if (!response.body) throw 'No image found';

        this.imageElement = document.getElementById('imageViewer') as HTMLImageElement;
        this.imageElement.src = 'data:image/jpg;base64,' + response.body.picture;
        // Give the time to the image to load
        setTimeout(() => {
          if (this.imageElement.naturalWidth > window.innerWidth) {
            this.imageElement.className = 'img_100 ';
            this.imageElement.className = this.imageElement.className + this.getZoomIcon();
          } else {
            this.imageElement.className = this.getZoomIcon();
          }
          this.isLoading = false;
        }, 100);
      });
    });
  }

  zoom(event: any): void {
    if (window.innerWidth > 800) {
      this.zoomInCoordinates(event.layerX, event.layerY);
    }
  }

  zoomInCoordinates(x: number, y: number): void {
    // Don't touch className position
    if (!this.imageElement.parentElement || !this.imageElement.parentElement.parentElement) throw 'Image parent error';

    const imageGranparent = this.imageElement.parentElement.parentElement;
    let calculatedTop: number;
    let calculatedLeft: number;
    if (this.imageElement.naturalHeight !== this.imageElement.height) {
      calculatedTop = (y * this.imageElement.naturalHeight) / this.imageElement.height - window.innerHeight / 2;
      calculatedLeft = (x * this.imageElement.naturalWidth) / this.imageElement.width - window.innerWidth / 2;
      this.imageElement.className = '';
    } else {
      this.imageElement.className = 'img_100';
      calculatedTop = (this.imageElement.height * y) / this.imageElement.naturalHeight - window.innerHeight / 2;
      calculatedLeft = 0;
    }
    this.imageElement.className = this.imageElement.className + ' ' + this.getZoomIcon();
    imageGranparent.scrollTop = calculatedTop;
    imageGranparent.scrollLeft = calculatedLeft;
  }

  getZoomIcon(): string {
    if (this.imageElement.naturalWidth > window.innerWidth) {
      return this.imageElement.width < this.imageElement.naturalWidth ? 'zoom_in' : 'zoom_out';
    } else {
      return this.imageElement.width > this.imageElement.naturalWidth ? 'zoom_out' : 'zoom_in';
    }
  }
}
