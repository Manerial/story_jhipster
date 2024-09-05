import { element, by, ElementFinder } from 'protractor';

export class SceneComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-scene div table .btn-danger'));
  title = element.all(by.css('jhi-scene div h2#page-heading span')).first();
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

export class SceneUpdatePage {
  pageTitle = element(by.id('jhi-scene-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  numberInput = element(by.id('field_number'));
  textInput = element(by.id('field_text'));
  timestampStartInput = element(by.id('field_timestampStart'));

  imageSelect = element(by.id('field_image'));
  chapterSelect = element(by.id('field_chapter'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setNumberInput(number: string): Promise<void> {
    await this.numberInput.sendKeys(number);
  }

  async getNumberInput(): Promise<string> {
    return await this.numberInput.getAttribute('value');
  }

  async setTextInput(text: string): Promise<void> {
    await this.textInput.sendKeys(text);
  }

  async getTextInput(): Promise<string> {
    return await this.textInput.getAttribute('value');
  }

  async setTimestampStartInput(timestampStart: string): Promise<void> {
    await this.timestampStartInput.sendKeys(timestampStart);
  }

  async getTimestampStartInput(): Promise<string> {
    return await this.timestampStartInput.getAttribute('value');
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

  async chapterSelectLastOption(): Promise<void> {
    await this.chapterSelect.all(by.tagName('option')).last().click();
  }

  async chapterSelectOption(option: string): Promise<void> {
    await this.chapterSelect.sendKeys(option);
  }

  getChapterSelect(): ElementFinder {
    return this.chapterSelect;
  }

  async getChapterSelectedOption(): Promise<string> {
    return await this.chapterSelect.element(by.css('option:checked')).getText();
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

export class SceneDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-scene-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-scene'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
