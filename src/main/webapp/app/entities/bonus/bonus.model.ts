export interface IBonus {
  id: number;
  name?: string | null;
  extension?: string | null;
}

export type NewBonus = Omit<IBonus, 'id'> & { id: null };
