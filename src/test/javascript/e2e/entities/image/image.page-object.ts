import { element, by, ElementFinder } from 'protractor';

export class ImageComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-image div table .btn-danger'));
  title = element.all(by.css('jhi-image div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ImageUpdatePage {
  pageTitle = element(by.id('jhi-image-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  pictureInput = element(by.id('file_picture'));
  previewInput = element(by.id('file_preview'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setPictureInput(picture: string): Promise<void> {
    await this.pictureInput.sendKeys(picture);
  }

  async getPictureInput(): Promise<string> {
    return await this.pictureInput.getAttribute('value');
  }

  async setPreviewInput(preview: string): Promise<void> {
    await this.previewInput.sendKeys(preview);
  }

  async getPreviewInput(): Promise<string> {
    return await this.previewInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ImageDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-image-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-image'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
