export class Income{
    constructor(private _amount: Number){
        this._amount = _amount;
    }

    public get amount(){
        return this._amount;
    }

    public set amount(amount: Number){
        this._amount = amount;
    }
}