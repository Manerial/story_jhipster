import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LibraryComponentsPage, LibraryDeleteDialog, LibraryUpdatePage } from './library.page-object';

const expect = chai.expect;

describe('Library e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let libraryComponentsPage: LibraryComponentsPage;
  let libraryUpdatePage: LibraryUpdatePage;
  let libraryDeleteDialog: LibraryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Libraries', async () => {
    await navBarPage.goToEntity('library');
    libraryComponentsPage = new LibraryComponentsPage();
    await browser.wait(ec.visibilityOf(libraryComponentsPage.title), 5000);
    expect(await libraryComponentsPage.getTitle()).to.eq('nidAuxHistoiresApp.library.home.title');
    await browser.wait(ec.or(ec.visibilityOf(libraryComponentsPage.entities), ec.visibilityOf(libraryComponentsPage.noResult)), 1000);
  });

  it('should load create Library page', async () => {
    await libraryComponentsPage.clickOnCreateButton();
    libraryUpdatePage = new LibraryUpdatePage();
    expect(await libraryUpdatePage.getPageTitle()).to.eq('nidAuxHistoiresApp.library.home.createOrEditLabel');
    await libraryUpdatePage.cancel();
  });

  it('should create and save Libraries', async () => {
    const nbButtonsBeforeCreate = await libraryComponentsPage.countDeleteButtons();

    await libraryComponentsPage.clickOnCreateButton();

    await promise.all([libraryUpdatePage.bookSelectLastOption(), libraryUpdatePage.curentChapterSelectLastOption()]);

    const selectedFinished = libraryUpdatePage.getFinishedInput();
    if (await selectedFinished.isSelected()) {
      await libraryUpdatePage.getFinishedInput().click();
      expect(await libraryUpdatePage.getFinishedInput().isSelected(), 'Expected finished not to be selected').to.be.false;
    } else {
      await libraryUpdatePage.getFinishedInput().click();
      expect(await libraryUpdatePage.getFinishedInput().isSelected(), 'Expected finished to be selected').to.be.true;
    }
    const selectedFavorit = libraryUpdatePage.getFavoritInput();
    if (await selectedFavorit.isSelected()) {
      await libraryUpdatePage.getFavoritInput().click();
      expect(await libraryUpdatePage.getFavoritInput().isSelected(), 'Expected favorit not to be selected').to.be.false;
    } else {
      await libraryUpdatePage.getFavoritInput().click();
      expect(await libraryUpdatePage.getFavoritInput().isSelected(), 'Expected favorit to be selected').to.be.true;
    }

    await libraryUpdatePage.save();
    expect(await libraryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await libraryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Library', async () => {
    const nbButtonsBeforeDelete = await libraryComponentsPage.countDeleteButtons();
    await libraryComponentsPage.clickOnLastDeleteButton();

    libraryDeleteDialog = new LibraryDeleteDialog();
    expect(await libraryDeleteDialog.getDialogTitle()).to.eq('nidAuxHistoiresApp.library.delete.question');
    await libraryDeleteDialog.clickOnConfirmButton();

    expect(await libraryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
