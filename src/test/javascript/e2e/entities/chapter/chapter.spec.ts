import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ChapterComponentsPage, ChapterDeleteDialog, ChapterUpdatePage } from './chapter.page-object';

const expect = chai.expect;

describe('Chapter e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let chapterComponentsPage: ChapterComponentsPage;
  let chapterUpdatePage: ChapterUpdatePage;
  let chapterDeleteDialog: ChapterDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Chapters', async () => {
    await navBarPage.goToEntity('chapter');
    chapterComponentsPage = new ChapterComponentsPage();
    await browser.wait(ec.visibilityOf(chapterComponentsPage.title), 5000);
    expect(await chapterComponentsPage.getTitle()).to.eq('nidAuxHistoiresApp.chapter.home.title');
    await browser.wait(ec.or(ec.visibilityOf(chapterComponentsPage.entities), ec.visibilityOf(chapterComponentsPage.noResult)), 1000);
  });

  it('should load create Chapter page', async () => {
    await chapterComponentsPage.clickOnCreateButton();
    chapterUpdatePage = new ChapterUpdatePage();
    expect(await chapterUpdatePage.getPageTitle()).to.eq('nidAuxHistoiresApp.chapter.home.createOrEditLabel');
    await chapterUpdatePage.cancel();
  });

  it('should create and save Chapters', async () => {
    const nbButtonsBeforeCreate = await chapterComponentsPage.countDeleteButtons();

    await chapterComponentsPage.clickOnCreateButton();

    await promise.all([
      chapterUpdatePage.setNameInput('name'),
      chapterUpdatePage.setDescriptionInput('description'),
      chapterUpdatePage.setNumberInput('5'),
      // chapterUpdatePage.imageSelectLastOption(),
      chapterUpdatePage.partSelectLastOption(),
    ]);

    expect(await chapterUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await chapterUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await chapterUpdatePage.getNumberInput()).to.eq('5', 'Expected number value to be equals to 5');

    await chapterUpdatePage.save();
    expect(await chapterUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await chapterComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Chapter', async () => {
    const nbButtonsBeforeDelete = await chapterComponentsPage.countDeleteButtons();
    await chapterComponentsPage.clickOnLastDeleteButton();

    chapterDeleteDialog = new ChapterDeleteDialog();
    expect(await chapterDeleteDialog.getDialogTitle()).to.eq('nidAuxHistoiresApp.chapter.delete.question');
    await chapterDeleteDialog.clickOnConfirmButton();

    expect(await chapterComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
