export interface IIdea {
  id: number;
  type?: string | null;
  value?: string | null;
  complement?: string | null;
}

export type NewIdea = Omit<IIdea, 'id'> & { id: null };
