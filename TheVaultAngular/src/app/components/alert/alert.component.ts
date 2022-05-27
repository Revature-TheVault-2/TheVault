import { Component, OnInit } from '@angular/core';
import { GlobalStorageService } from 'src/app/_services/global-storage.service';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css']
})
export class AlertComponent implements OnInit {
  get transSuccess(){
    return this.globalStorage.transSuccess;
  }

  get transFail(){
    return this.globalStorage.transFail;
  }


  setTransSuccess(value:boolean){
    this.globalStorage.transSuccess=value;
  }

  setTransFail(value:boolean){
    this.globalStorage.transFail=value;
  }

  constructor(private globalStorage: GlobalStorageService) { }
  
  ngOnInit(): void {
  }

}
