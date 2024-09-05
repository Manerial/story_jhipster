import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SceneComponentsPage, SceneDeleteDialog, SceneUpdatePage } from './scene.page-object';

const expect = chai.expect;

describe('Scene e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let sceneComponentsPage: SceneComponentsPage;
  let sceneUpdatePage: SceneUpdatePage;
  let sceneDeleteDialog: SceneDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Scenes', async () => {
    await navBarPage.goToEntity('scene');
    sceneComponentsPage = new SceneComponentsPage();
    await browser.wait(ec.visibilityOf(sceneComponentsPage.title), 5000);
    expect(await sceneComponentsPage.getTitle()).to.eq('nidAuxHistoiresApp.scene.home.title');
    await browser.wait(ec.or(ec.visibilityOf(sceneComponentsPage.entities), ec.visibilityOf(sceneComponentsPage.noResult)), 1000);
  });

  it('should load create Scene page', async () => {
    await sceneComponentsPage.clickOnCreateButton();
    sceneUpdatePage = new SceneUpdatePage();
    expect(await sceneUpdatePage.getPageTitle()).to.eq('nidAuxHistoiresApp.scene.home.createOrEditLabel');
    await sceneUpdatePage.cancel();
  });

  it('should create and save Scenes', async () => {
    const nbButtonsBeforeCreate = await sceneComponentsPage.countDeleteButtons();

    await sceneComponentsPage.clickOnCreateButton();

    await promise.all([
      sceneUpdatePage.setNameInput('name'),
      sceneUpdatePage.setNumberInput('5'),
      sceneUpdatePage.setTextInput('text'),
      sceneUpdatePage.setTimestampStartInput('2000-12-31'),
      // sceneUpdatePage.imageSelectLastOption(),
      sceneUpdatePage.chapterSelectLastOption(),
    ]);

    expect(await sceneUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await sceneUpdatePage.getNumberInput()).to.eq('5', 'Expected number value to be equals to 5');
    expect(await sceneUpdatePage.getTextInput()).to.eq('text', 'Expected Text value to be equals to text');
    expect(await sceneUpdatePage.getTimestampStartInput()).to.eq('2000-12-31', 'Expected timestampStart value to be equals to 2000-12-31');

    await sceneUpdatePage.save();
    expect(await sceneUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await sceneComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Scene', async () => {
    const nbButtonsBeforeDelete = await sceneComponentsPage.countDeleteButtons();
    await sceneComponentsPage.clickOnLastDeleteButton();

    sceneDeleteDialog = new SceneDeleteDialog();
    expect(await sceneDeleteDialog.getDialogTitle()).to.eq('nidAuxHistoiresApp.scene.delete.question');
    await sceneDeleteDialog.clickOnConfirmButton();

    expect(await sceneComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
