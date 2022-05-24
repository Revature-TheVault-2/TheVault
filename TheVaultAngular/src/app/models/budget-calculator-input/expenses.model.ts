export class Expense{
    constructor(private _name: string, private _amount: number){
        this._name = _name;
        this._amount = _amount;
    }

    public get name(){
        return this._name;
    }

    public get amount(){
        return this._amount;
    }

    public set name(value: string){
        this._name = value;
    }

    public set amount(value: number){
        this._amount
    }
}