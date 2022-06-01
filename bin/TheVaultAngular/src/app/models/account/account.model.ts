export class Account {

    constructor(
        private _accountId: number,
        private _userId: number,
        private _accountType: string,
        private _availableBalance: number,
        private _pendingBalance: number,
        private _email: string
    ){
        this._accountId = _accountId;
        this._userId = _userId;
        this._accountType = _accountType;
        this._availableBalance = _availableBalance;
        this._pendingBalance = _pendingBalance;
        this._email = _email;
    }

    public get email(): string {
        return this._email;
    }
    public set email(value: string) {
        this._email = value;
    }
    public get pendingBalance(): number {
        return this._pendingBalance;
    }
    public set pendingBalance(value: number) {
        this._pendingBalance = value;
    }
    public get availableBalance(): number {
        return this._availableBalance;
    }
    public set availableBalance(value: number) {
        this._availableBalance = value;
    }
    public get accountType(): string {
        return this._accountType;
    }
  /* istanbul ignore next */
    public set accountType(value: string) {
        this._accountType = value;
    }

  /* istanbul ignore next */
    public get userId(): number {
        return this._userId;
    }
  /* istanbul ignore next */
    public set userId(value: number) {
        this._userId = value;
    }
  /* istanbul ignore next */
    public get accountId(): number {
        return this._accountId;
    }
  /* istanbul ignore next */
    public set accountId(value: number) {
        this._accountId = value;
    }
    

}
