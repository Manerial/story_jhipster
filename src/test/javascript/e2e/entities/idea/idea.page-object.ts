import { element, by, ElementFinder } from 'protractor';

export class IdeaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-idea div table .btn-danger'));
  title = element.all(by.css('jhi-idea div h2#page-heading span')).first();
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

export class IdeaUpdatePage {
  pageTitle = element(by.id('jhi-idea-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  typeInput = element(by.id('field_type'));
  valueInput = element(by.id('field_value'));
  complementInput = element(by.id('field_complement'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTypeInput(type: string): Promise<void> {
    await this.typeInput.sendKeys(type);
  }

  async getTypeInput(): Promise<string> {
    return await this.typeInput.getAttribute('value');
  }

  async setValueInput(value: string): Promise<void> {
    await this.valueInput.sendKeys(value);
  }

  async getValueInput(): Promise<string> {
    return await this.valueInput.getAttribute('value');
  }

  async setComplementInput(complement: string): Promise<void> {
    await this.complementInput.sendKeys(complement);
  }

  async getComplementInput(): Promise<string> {
    return await this.complementInput.getAttribute('value');
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

export class IdeaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-idea-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-idea'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
