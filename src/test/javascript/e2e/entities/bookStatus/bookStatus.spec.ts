import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BookStatusComponentsPage, BookStatusDeleteDialog, BookStatusUpdatePage } from './bookStatus.page-object';

const expect = chai.expect;

describe('BookStatus e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let bookStatusComponentsPage: BookStatusComponentsPage;
  let bookStatusUpdatePage: BookStatusUpdatePage;
  let bookStatusDeleteDialog: BookStatusDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Libraries', async () => {
    await navBarPage.goToEntity('bookStatus');
    bookStatusComponentsPage = new BookStatusComponentsPage();
    await browser.wait(ec.visibilityOf(bookStatusComponentsPage.title), 5000);
    expect(await bookStatusComponentsPage.getTitle()).to.eq('nidAuxHistoiresApp.bookStatus.home.title');
    await browser.wait(ec.or(ec.visibilityOf(bookStatusComponentsPage.entities), ec.visibilityOf(bookStatusComponentsPage.noResult)), 1000);
  });

  it('should load create BookStatus page', async () => {
    await bookStatusComponentsPage.clickOnCreateButton();
    bookStatusUpdatePage = new BookStatusUpdatePage();
    expect(await bookStatusUpdatePage.getPageTitle()).to.eq('nidAuxHistoiresApp.bookStatus.home.createOrEditLabel');
    await bookStatusUpdatePage.cancel();
  });

  it('should create and save Libraries', async () => {
    const nbButtonsBeforeCreate = await bookStatusComponentsPage.countDeleteButtons();

    await bookStatusComponentsPage.clickOnCreateButton();

    await promise.all([bookStatusUpdatePage.bookSelectLastOption(), bookStatusUpdatePage.curentChapterSelectLastOption()]);

    const selectedFinished = bookStatusUpdatePage.getFinishedInput();
    if (await selectedFinished.isSelected()) {
      await bookStatusUpdatePage.getFinishedInput().click();
      expect(await bookStatusUpdatePage.getFinishedInput().isSelected(), 'Expected finished not to be selected').to.be.false;
    } else {
      await bookStatusUpdatePage.getFinishedInput().click();
      expect(await bookStatusUpdatePage.getFinishedInput().isSelected(), 'Expected finished to be selected').to.be.true;
    }
    const selectedFavorit = bookStatusUpdatePage.getFavoritInput();
    if (await selectedFavorit.isSelected()) {
      await bookStatusUpdatePage.getFavoritInput().click();
      expect(await bookStatusUpdatePage.getFavoritInput().isSelected(), 'Expected favorit not to be selected').to.be.false;
    } else {
      await bookStatusUpdatePage.getFavoritInput().click();
      expect(await bookStatusUpdatePage.getFavoritInput().isSelected(), 'Expected favorit to be selected').to.be.true;
    }

    await bookStatusUpdatePage.save();
    expect(await bookStatusUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await bookStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last BookStatus', async () => {
    const nbButtonsBeforeDelete = await bookStatusComponentsPage.countDeleteButtons();
    await bookStatusComponentsPage.clickOnLastDeleteButton();

    bookStatusDeleteDialog = new BookStatusDeleteDialog();
    expect(await bookStatusDeleteDialog.getDialogTitle()).to.eq('nidAuxHistoiresApp.bookStatus.delete.question');
    await bookStatusDeleteDialog.clickOnConfirmButton();

    expect(await bookStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
