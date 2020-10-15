import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IdeaComponentsPage, IdeaDeleteDialog, IdeaUpdatePage } from './idea.page-object';

const expect = chai.expect;

describe('Idea e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let ideaComponentsPage: IdeaComponentsPage;
  let ideaUpdatePage: IdeaUpdatePage;
  let ideaDeleteDialog: IdeaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Ideas', async () => {
    await navBarPage.goToEntity('idea');
    ideaComponentsPage = new IdeaComponentsPage();
    await browser.wait(ec.visibilityOf(ideaComponentsPage.title), 5000);
    expect(await ideaComponentsPage.getTitle()).to.eq('nidAuxHistoiresApp.idea.home.title');
    await browser.wait(ec.or(ec.visibilityOf(ideaComponentsPage.entities), ec.visibilityOf(ideaComponentsPage.noResult)), 1000);
  });

  it('should load create Idea page', async () => {
    await ideaComponentsPage.clickOnCreateButton();
    ideaUpdatePage = new IdeaUpdatePage();
    expect(await ideaUpdatePage.getPageTitle()).to.eq('nidAuxHistoiresApp.idea.home.createOrEditLabel');
    await ideaUpdatePage.cancel();
  });

  it('should create and save Ideas', async () => {
    const nbButtonsBeforeCreate = await ideaComponentsPage.countDeleteButtons();

    await ideaComponentsPage.clickOnCreateButton();

    await promise.all([
      ideaUpdatePage.setTypeInput('type'),
      ideaUpdatePage.setValueInput('value'),
      ideaUpdatePage.setComplementInput('complement'),
    ]);

    expect(await ideaUpdatePage.getTypeInput()).to.eq('type', 'Expected Type value to be equals to type');
    expect(await ideaUpdatePage.getValueInput()).to.eq('value', 'Expected Value value to be equals to value');
    expect(await ideaUpdatePage.getComplementInput()).to.eq('complement', 'Expected Complement value to be equals to complement');

    await ideaUpdatePage.save();
    expect(await ideaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await ideaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Idea', async () => {
    const nbButtonsBeforeDelete = await ideaComponentsPage.countDeleteButtons();
    await ideaComponentsPage.clickOnLastDeleteButton();

    ideaDeleteDialog = new IdeaDeleteDialog();
    expect(await ideaDeleteDialog.getDialogTitle()).to.eq('nidAuxHistoiresApp.idea.delete.question');
    await ideaDeleteDialog.clickOnConfirmButton();

    expect(await ideaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
