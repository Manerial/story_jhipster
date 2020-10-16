export interface IPhysical {
  hair_color?: string;
  hair_style?: string;
  eyes_color?: string;
  face_shape?: string;
  gender?: string;
  height?: number;
  weight?: number;
  morphology?: string;
}

export class Physical implements IPhysical {
  constructor(
    public hair_color?: string,
    public hair_style?: string,
    public eyes_color?: string,
    public face_shape?: string,
    public gender?: string,
    public height?: number,
    public weight?: number,
    public morphology?: string
  ) {}
}
