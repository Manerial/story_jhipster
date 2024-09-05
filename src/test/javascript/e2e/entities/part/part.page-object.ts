import { element, by, ElementFinder } from 'protractor';

export class PartComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-part div table .btn-danger'));
  title = element.all(by.css('jhi-part div h2#page-heading span')).first();
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

export class PartUpdatePage {
  pageTitle = element(by.id('jhi-part-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  numberInput = element(by.id('field_number'));

  imageSelect = element(by.id('field_image'));
  bookSelect = element(by.id('field_book'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setNumberInput(number: string): Promise<void> {
    await this.numberInput.sendKeys(number);
  }

  async getNumberInput(): Promise<string> {
    return await this.numberInput.getAttribute('value');
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

  async bookSelectLastOption(): Promise<void> {
    await this.bookSelect.all(by.tagName('option')).last().click();
  }

  async bookSelectOption(option: string): Promise<void> {
    await this.bookSelect.sendKeys(option);
  }

  getBookSelect(): ElementFinder {
    return this.bookSelect;
  }

  async getBookSelectedOption(): Promise<string> {
    return await this.bookSelect.element(by.css('option:checked')).getText();
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

export class PartDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-part-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-part'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
