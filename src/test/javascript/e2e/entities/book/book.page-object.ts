import { element, by, ElementFinder } from 'protractor';

export class BookComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-book div table .btn-danger'));
  title = element.all(by.css('jhi-book div h2#page-heading span')).first();
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

export class BookUpdatePage {
  pageTitle = element(by.id('jhi-book-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  authorInput = element(by.id('field_author'));

  imageSelect = element(by.id('field_image'));
  coverSelect = element(by.id('field_cover'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setAuthorInput(author: string): Promise<void> {
    await this.authorInput.sendKeys(author);
  }

  async getAuthorInput(): Promise<string> {
    return await this.authorInput.getAttribute('value');
  }

  async imageSelectLastOption(): Promise<void> {
    await this.imageSelect.all(by.tagName('option')).last().click();
  }

  async imageSelectOption(option: string): Promise<void> {
    await this.imageSelect.sendKeys(option);
  }

  getImageSelect(): ElementFinder {
    return this.imageSelect;
  }

  async getImageSelectedOption(): Promise<string> {
    return await this.imageSelect.element(by.css('option:checked')).getText();
  }

  async coverSelectLastOption(): Promise<void> {
    await this.coverSelect.all(by.tagName('option')).last().click();
  }

  async coverSelectOption(option: string): Promise<void> {
    await this.coverSelect.sendKeys(option);
  }

  getCoverSelect(): ElementFinder {
    return this.coverSelect;
  }

  async getCoverSelectedOption(): Promise<string> {
    return await this.coverSelect.element(by.css('option:checked')).getText();
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

export class BookDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-book-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-book'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
