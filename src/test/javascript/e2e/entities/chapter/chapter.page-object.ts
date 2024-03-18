import { element, by, ElementFinder } from 'protractor';

export class ChapterComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-chapter div table .btn-danger'));
  title = element.all(by.css('jhi-chapter div h2#page-heading span')).first();
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

export class ChapterUpdatePage {
  pageTitle = element(by.id('jhi-chapter-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  numberInput = element(by.id('field_number'));

  imageSelect = element(by.id('field_image'));
  partSelect = element(by.id('field_part'));

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

  async partSelectLastOption(): Promise<void> {
    await this.partSelect.all(by.tagName('option')).last().click();
  }

  async partSelectOption(option: string): Promise<void> {
    await this.partSelect.sendKeys(option);
  }

  getPartSelect(): ElementFinder {
    return this.partSelect;
  }

  async getPartSelectedOption(): Promise<string> {
    return await this.partSelect.element(by.css('option:checked')).getText();
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

export class ChapterDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-chapter-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-chapter'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
