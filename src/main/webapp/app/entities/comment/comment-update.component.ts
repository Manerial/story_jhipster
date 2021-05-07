import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IComment, Comment } from 'app/shared/model/comment.model';
import { CommentService } from './comment.service';
import { IBook } from 'app/shared/model/book.model';
import { IUser } from 'app/core/user/user.model';
import { BookService } from '../book/book.service';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IBook | IUser;

@Component({
  selector: 'jhi-comment-update',
  templateUrl: './comment-update.component.html',
})
export class CommentUpdateComponent implements OnInit {
  isSaving = false;
  books: IBook[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    text: [],
    bookId: null,
    userLogin: null,
  });

  constructor(
    protected commentService: CommentService,
    protected bookService: BookService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comment }) => {
      this.updateForm(comment);
    });
    this.bookService.query().subscribe((res: HttpResponse<IBook[]>) => (this.books = res.body || []));
    this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
  }

  updateForm(comment: IComment): void {
    this.editForm.patchValue({
      id: comment.id,
      text: comment.text,
      bookId: comment.bookId,
      userLogin: comment.userLogin,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comment = this.createFromForm();
    if (comment.id !== undefined) {
      this.subscribeToSaveResponse(this.commentService.update(comment));
    } else {
      this.subscribeToSaveResponse(this.commentService.create(comment));
    }
  }

  private createFromForm(): IComment {
    return {
      ...new Comment(),
      id: this.editForm.get(['id'])!.value,
      text: this.editForm.get(['text'])!.value,
      bookId: this.editForm.get(['bookId'])!.value,
      userLogin: this.editForm.get(['userLogin'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComment>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
