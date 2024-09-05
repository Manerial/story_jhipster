export interface IQuest {
  giver: string;
  receiver: string;
  objective: string;
  hazard: string;
  consequence: string;
}

export class Quest implements IQuest {
  public giver: string;
  public receiver: string;
  public objective: string;
  public hazard: string;
  public consequence: string;

  constructor();

  constructor(giver?: string, receiver?: string, objective?: string, hazard?: string, consequence?: string) {
    this.giver = giver || '';
    this.receiver = receiver || '';
    this.objective = objective || '';
    this.hazard = hazard || '';
    this.consequence = consequence || '';
  }
}
