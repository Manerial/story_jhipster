export interface IUser {
  id: any;
  login: string;
  firstName: string;
  lastName: string;
  email: string;
  activated: boolean;
  langKey: string;
  authorities: string[];
  createdBy: string;
  createdDate: Date;
  lastModifiedBy: string;
  lastModifiedDate: Date;
  password: string;
}

export class User implements IUser {
  public id: any;
  public login: string;
  public firstName: string;
  public lastName: string;
  public email: string;
  public activated: boolean;
  public langKey: string;
  public authorities: string[];
  public createdBy: string;
  public createdDate: Date;
  public lastModifiedBy: string;
  public lastModifiedDate: Date;
  public password: string;

  constructor();

  constructor(
    id?: any,
    login?: string,
    firstName?: string,
    lastName?: string,
    email?: string,
    activated?: boolean,
    langKey?: string,
    authorities?: string[],
    createdBy?: string,
    createdDate?: Date,
    lastModifiedBy?: string,
    lastModifiedDate?: Date,
    password?: string
  );

  constructor(
    id?: any,
    login?: string,
    firstName?: string,
    lastName?: string,
    email?: string,
    activated?: boolean,
    langKey?: string,
    authorities?: string[],
    createdBy?: string,
    createdDate?: Date,
    lastModifiedBy?: string,
    lastModifiedDate?: Date,
    password?: string
  ) {
    this.id = id || null;
    this.login = login || '';
    this.firstName = firstName || '';
    this.lastName = lastName || '';
    this.email = email || '';
    this.activated = activated || true;
    this.langKey = langKey || '';
    this.authorities = authorities || [];
    this.createdBy = createdBy || '';
    this.createdDate = createdDate || new Date();
    this.lastModifiedBy = lastModifiedBy || '';
    this.lastModifiedDate = lastModifiedDate || new Date();
    this.password = password || '';
  }
}
