import { Component, OnInit } from '@angular/core';
import { Account } from 'src/app/models/account/account.model';
import { AccountService } from 'src/app/_services/account/account.service';
import { UserSessionService } from 'src/app/_services/user/user-session.service';
import { UserService } from 'src/app/_services/user/user.service';

@Component({
  selector: 'app-account-view',
  templateUrl: './account-view.component.html',
  styleUrls: ['./account-view.component.css']
})
export class AccountViewComponent implements OnInit {

  userId!:number;

  name!:string;

  constructor(
    private accountService: AccountService,
    private userService: UserService,
    private userSession: UserSessionService) { }

    ngOnInit(): void {
      this.userId = this.userSession.getUser().id;
      this.name = this.userSession.getUser().firstName;
    }

}
