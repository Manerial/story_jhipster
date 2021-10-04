import { element, by, ElementFinder } from 'protractor';

export class BookStatusComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-library div table .btn-danger'));
  title = element.all(by.css('jhi-library div h2#page-heading span')).first();
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

export class BookStatusUpdatePage {
  pageTitle = element(by.id('jhi-library-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  finishedInput = element(by.id('field_finished'));
  favoritInput = element(by.id('field_favorit'));

  bookSelect = element(by.id('field_book'));
  curentChapterSelect = element(by.id('field_curentChapter'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  getFinishedInput(): ElementFinder {
    return this.finishedInput;
  }

  getFavoritInput(): ElementFinder {
    return this.favoritInput;
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

  async curentChapterSelectLastOption(): Promise<void> {
    await this.curentChapterSelect.all(by.tagName('option')).last().click();
  }

  async curentChapterSelectOption(option: string): Promise<void> {
    await this.curentChapterSelect.sendKeys(option);
  }

  getCurentChapterSelect(): ElementFinder {
    return this.curentChapterSelect;
  }

  async getCurentChapterSelectedOption(): Promise<string> {
    return await this.curentChapterSelect.element(by.css('option:checked')).getText();
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

export class BookStatusDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-bookStatus-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-bookStatus'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
