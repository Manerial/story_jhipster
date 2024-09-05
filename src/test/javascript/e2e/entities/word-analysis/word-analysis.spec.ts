import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { WordAnalysisComponentsPage, WordAnalysisDeleteDialog, WordAnalysisUpdatePage } from './word-analysis.page-object';

const expect = chai.expect;

describe('WordAnalysis e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let wordAnalysisComponentsPage: WordAnalysisComponentsPage;
  let wordAnalysisUpdatePage: WordAnalysisUpdatePage;
  let wordAnalysisDeleteDialog: WordAnalysisDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load WordAnalyses', async () => {
    await navBarPage.goToEntity('word-analysis');
    wordAnalysisComponentsPage = new WordAnalysisComponentsPage();
    await browser.wait(ec.visibilityOf(wordAnalysisComponentsPage.title), 5000);
    expect(await wordAnalysisComponentsPage.getTitle()).to.eq('nidAuxHistoiresApp.wordAnalysis.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(wordAnalysisComponentsPage.entities), ec.visibilityOf(wordAnalysisComponentsPage.noResult)),
      1000
    );
  });

  it('should load create WordAnalysis page', async () => {
    await wordAnalysisComponentsPage.clickOnCreateButton();
    wordAnalysisUpdatePage = new WordAnalysisUpdatePage();
    expect(await wordAnalysisUpdatePage.getPageTitle()).to.eq('nidAuxHistoiresApp.wordAnalysis.home.createOrEditLabel');
    await wordAnalysisUpdatePage.cancel();
  });

  it('should create and save WordAnalyses', async () => {
    const nbButtonsBeforeCreate = await wordAnalysisComponentsPage.countDeleteButtons();

    await wordAnalysisComponentsPage.clickOnCreateButton();

    await promise.all([
      wordAnalysisUpdatePage.setTypeInput('type'),
      wordAnalysisUpdatePage.setNameInput('name'),
      wordAnalysisUpdatePage.setAnalysisInput('analysis'),
    ]);

    expect(await wordAnalysisUpdatePage.getTypeInput()).to.eq('type', 'Expected Type value to be equals to type');
    expect(await wordAnalysisUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await wordAnalysisUpdatePage.getAnalysisInput()).to.eq('analysis', 'Expected Analysis value to be equals to analysis');

    await wordAnalysisUpdatePage.save();
    expect(await wordAnalysisUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await wordAnalysisComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last WordAnalysis', async () => {
    const nbButtonsBeforeDelete = await wordAnalysisComponentsPage.countDeleteButtons();
    await wordAnalysisComponentsPage.clickOnLastDeleteButton();

    wordAnalysisDeleteDialog = new WordAnalysisDeleteDialog();
    expect(await wordAnalysisDeleteDialog.getDialogTitle()).to.eq('nidAuxHistoiresApp.wordAnalysis.delete.question');
    await wordAnalysisDeleteDialog.clickOnConfirmButton();

    expect(await wordAnalysisComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
