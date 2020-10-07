import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PartComponentsPage, PartDeleteDialog, PartUpdatePage } from './part.page-object';

const expect = chai.expect;

describe('Part e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let partComponentsPage: PartComponentsPage;
  let partUpdatePage: PartUpdatePage;
  let partDeleteDialog: PartDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Parts', async () => {
    await navBarPage.goToEntity('part');
    partComponentsPage = new PartComponentsPage();
    await browser.wait(ec.visibilityOf(partComponentsPage.title), 5000);
    expect(await partComponentsPage.getTitle()).to.eq('nidAuxHistoiresApp.part.home.title');
    await browser.wait(ec.or(ec.visibilityOf(partComponentsPage.entities), ec.visibilityOf(partComponentsPage.noResult)), 1000);
  });

  it('should load create Part page', async () => {
    await partComponentsPage.clickOnCreateButton();
    partUpdatePage = new PartUpdatePage();
    expect(await partUpdatePage.getPageTitle()).to.eq('nidAuxHistoiresApp.part.home.createOrEditLabel');
    await partUpdatePage.cancel();
  });

  it('should create and save Parts', async () => {
    const nbButtonsBeforeCreate = await partComponentsPage.countDeleteButtons();

    await partComponentsPage.clickOnCreateButton();

    await promise.all([
      partUpdatePage.setNameInput('name'),
      partUpdatePage.setDescriptionInput('description'),
      partUpdatePage.setNumberInput('5'),
      // partUpdatePage.imageSelectLastOption(),
      partUpdatePage.bookSelectLastOption(),
    ]);

    expect(await partUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await partUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await partUpdatePage.getNumberInput()).to.eq('5', 'Expected number value to be equals to 5');

    await partUpdatePage.save();
    expect(await partUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await partComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Part', async () => {
    const nbButtonsBeforeDelete = await partComponentsPage.countDeleteButtons();
    await partComponentsPage.clickOnLastDeleteButton();

    partDeleteDialog = new PartDeleteDialog();
    expect(await partDeleteDialog.getDialogTitle()).to.eq('nidAuxHistoiresApp.part.delete.question');
    await partDeleteDialog.clickOnConfirmButton();

    expect(await partComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
