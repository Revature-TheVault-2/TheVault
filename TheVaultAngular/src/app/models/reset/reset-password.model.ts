// import { stringify } from "querystring";

export class resetPassword {
  // /* istanbul ignore next */
  //   public get jwt(): string {
  //       return this._jwt;
  //   }
  // /* istanbul ignore next */
  //   public set jwt(value: string) {
  //       this._jwt = value;
  //   }
    constructor(
        private _token: string,
        private _password: string,
        // private _jwt: string
    ){
        this._token = _token;
        this._password = _password;
        // this._jwt = _jwt;
    }

  /* istanbul ignore next */
    public get password(): string {
        return this._password;
    }
  /* istanbul ignore next */
    public set password(value: string) {
        this._password = value;
    }
  

    public get token(): string {
        return this._token;
    }
  /* istanbul ignore next */
    public set token(value: string) {
        this._token = value;
    }
}
