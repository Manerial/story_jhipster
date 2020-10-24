import { ParamMap } from '@angular/router';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class UtilService {
  getParamNumber(params: ParamMap, param: string): number {
    const paramStr = params.get(param);
    if (paramStr === null) {
      throw 'Book Id is null';
    }
    return parseInt(paramStr, 10);
  }
}
