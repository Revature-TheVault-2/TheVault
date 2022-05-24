import { Component, ViewEncapsulation } from '@angular/core';
import { Profile } from './models/users/profile.model';
import { GlobalStorageService } from './_services/global-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent {
  title = 'TheVault';

  logout(){
    this.globalStorage.setProfile(new Profile(0,0,'','','','',''));
    this.globalStorage.setAccounts([]);
    this.globalStorage.setUserId(0);
    this.globalStorage.setUsername("");
    console.log("logout");
  }

  isLoggedIn(): boolean {
    return !!this.globalStorage.userId;
  }

  constructor(private globalStorage: GlobalStorageService){}
}
