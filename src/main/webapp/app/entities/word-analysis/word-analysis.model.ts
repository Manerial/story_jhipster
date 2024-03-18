export interface IWordAnalysis {
  id: number;
  type?: string | null;
  name?: string | null;
  analysis?: string | null;
}

export type NewWordAnalysis = Omit<IWordAnalysis, 'id'> & { id: null };
