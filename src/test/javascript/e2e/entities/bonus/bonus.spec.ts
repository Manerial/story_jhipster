import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BonusComponentsPage, BonusDeleteDialog, BonusUpdatePage } from './bonus.page-object';

const expect = chai.expect;

describe('Bonus e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let bonusComponentsPage: BonusComponentsPage;
  let bonusUpdatePage: BonusUpdatePage;
  let bonusDeleteDialog: BonusDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Bonuses', async () => {
    await navBarPage.goToEntity('bonus');
    bonusComponentsPage = new BonusComponentsPage();
    await browser.wait(ec.visibilityOf(bonusComponentsPage.title), 5000);
    expect(await bonusComponentsPage.getTitle()).to.eq('nidAuxHistoiresApp.bonus.home.title');
    await browser.wait(ec.or(ec.visibilityOf(bonusComponentsPage.entities), ec.visibilityOf(bonusComponentsPage.noResult)), 1000);
  });

  it('should load create Bonus page', async () => {
    await bonusComponentsPage.clickOnCreateButton();
    bonusUpdatePage = new BonusUpdatePage();
    expect(await bonusUpdatePage.getPageTitle()).to.eq('nidAuxHistoiresApp.bonus.home.createOrEditLabel');
    await bonusUpdatePage.cancel();
  });

  it('should create and save Bonuses', async () => {
    const nbButtonsBeforeCreate = await bonusComponentsPage.countDeleteButtons();

    await bonusComponentsPage.clickOnCreateButton();

    await promise.all([bonusUpdatePage.setNameInput('name'), bonusUpdatePage.setExtensionInput('extension')]);

    expect(await bonusUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await bonusUpdatePage.getExtensionInput()).to.eq('extension', 'Expected Extension value to be equals to extension');

    await bonusUpdatePage.save();
    expect(await bonusUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await bonusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Bonus', async () => {
    const nbButtonsBeforeDelete = await bonusComponentsPage.countDeleteButtons();
    await bonusComponentsPage.clickOnLastDeleteButton();

    bonusDeleteDialog = new BonusDeleteDialog();
    expect(await bonusDeleteDialog.getDialogTitle()).to.eq('nidAuxHistoiresApp.bonus.delete.question');
    await bonusDeleteDialog.clickOnConfirmButton();

    expect(await bonusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
