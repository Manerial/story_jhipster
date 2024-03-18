export interface IComment {
  id: number;
  text?: string | null;
}

export type NewComment = Omit<IComment, 'id'> & { id: null };
