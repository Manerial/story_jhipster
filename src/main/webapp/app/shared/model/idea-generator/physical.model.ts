export interface IPhysical {
  hairColor: string;
  hairStyle: string;
  eyesColor: string;
  faceShape: string;
  gender: string;
  height: number;
  weight: number;
  morphology: string;
}

export class Physical implements IPhysical {
  public hairColor: string;
  public hairStyle: string;
  public eyesColor: string;
  public faceShape: string;
  public gender: string;
  public height: number;
  public weight: number;
  public morphology: string;

  constructor();

  constructor(
    hairColor?: string,
    hairStyle?: string,
    eyesColor?: string,
    faceShape?: string,
    gender?: string,
    height?: number,
    weight?: number,
    morphology?: string
  ) {
    this.hairColor = hairColor || '';
    this.hairStyle = hairStyle || '';
    this.eyesColor = eyesColor || '';
    this.faceShape = faceShape || '';
    this.gender = gender || '';
    this.height = height || 0;
    this.weight = weight || 0;
    this.morphology = morphology || '';
  }
}
