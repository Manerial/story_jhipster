export interface IUser {
  id?: any;
  login?: string;
  firstName?: string;
  lastName?: string;
  books?: number;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: string[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  introduction?: string;
  password?: string;
}

export class User implements IUser {
  public id?: any;
  public login?: string;
  public firstName?: string;
  public lastName?: string;
  public books?: number;
  public email?: string;
  public activated?: boolean;
  public langKey?: string;
  public authorities?: string[];
  public createdBy?: string;
  public createdDate?: Date;
  public lastModifiedBy?: string;
  public lastModifiedDate?: Date;
  public introduction?: string;
  public password?: string;

  constructor();

  constructor(
    id?: any,
    login?: string,
    firstName?: string,
    lastName?: string,
    books?: number,
    email?: string,
    activated?: boolean,
    langKey?: string,
    authorities?: string[],
    createdBy?: string,
    createdDate?: Date,
    lastModifiedBy?: string,
    lastModifiedDate?: Date,
    introduction?: string,
    password?: string
  );

  constructor(
    id?: any,
    login?: string,
    firstName?: string,
    lastName?: string,
    books?: number,
    email?: string,
    activated?: boolean,
    langKey?: string,
    authorities?: string[],
    createdBy?: string,
    createdDate?: Date,
    lastModifiedBy?: string,
    lastModifiedDate?: Date,
    introduction?: string,
    password?: string
  ) {
    this.id = id;
    this.login = login;
    this.firstName = firstName;
    this.lastName = lastName;
    this.books = books;
    this.email = email;
    this.activated = activated;
    this.langKey = langKey;
    this.authorities = authorities;
    this.createdBy = createdBy;
    this.createdDate = createdDate;
    this.lastModifiedBy = lastModifiedBy;
    this.lastModifiedDate = lastModifiedDate;
    this.introduction = introduction;
    this.password = password;
  }
}
