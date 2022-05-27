import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { RoutingAllocatorService } from 'src/app/_services/app_control/routing-allocator.service';


import { PostLogin } from 'src/app/models/login/responses/post-login';
import { NewUser } from 'src/app/models/users/new-user.model';
import { PostProfile } from 'src/app/models/users/responses/post-profile';
import Validation from 'src/app/utils/validation';
import { UserHandlerService } from 'src/app/_services/user/user-handler.service';
import { LoginCredential} from 'src/app/models/login/login-credential.model';
import { LoginUser } from 'src/app/models/users/login-user.model';
import { GlobalStorageService } from 'src/app/_services/global-storage.service';
import { Token } from '@angular/compiler';
import { throwError } from 'rxjs';


@Component({
  selector: 'app-resetpassword',
  templateUrl: './resetpassword.component.html',
  styleUrls: ['./resetpassword.component.css']
})
export class ResetpasswordComponent implements OnInit {

  error:boolean = false;
  success:boolean = false;
  errorMessage: string = "Please double-check your Username and try again. Contact customer support if you continue to experience problems";
  successMessage: string = "Success! Check your email.";
  loginUser!: LoginUser

  form: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    
  });
  submitted = false;
  posts: any;

  // loginCredential!: LoginCredential
  
  constructor(
    private formBuilder: FormBuilder,
    private routingAllocator: RoutingAllocatorService,
    private userHandler: UserHandlerService,
    private globalStorage: GlobalStorageService,
    private router: RoutingAllocatorService
  ) { }




  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm():void{
    this.form = this.formBuilder.group(
      {        
        username: [
          '',
          [
            Validators.required,
            Validators.minLength(4),
            Validators.maxLength(25)
          ]
        ]
      }
    );
  }

  goToLogin(): void {
    this.routingAllocator.login();
  }

  get f(): { [key: string]: AbstractControl } {
    return this.form.controls;
  }

  onReset(): void {
    this.submitted = false;
    this.form.reset();
  }

  onSubmit(): void {
    this.submitted = true;

    // this.error = false;
    // this.errorMessage = "Error";

  /* istanbul ignore next */
    if (this.form.invalid) {
      return;
    }
    let userN = this.form.get('username')?.value;
    
    if (userN != null) {

      // this.newUser = new NewUser(userN, firstN, lastN, email, addr, phoneN, passW);
      this.loginUser = new LoginUser(userN,"");
      // this.credentials = loginUser;
      this.resetPassword();
    
  }

}
resetPassword(){
  this.userHandler.resetPassword(this.loginUser.username, this.loginUser.password).subscribe(this.loginObserver);
}

loginObserver = {
  next: (data: boolean) => {
    if (data){
      this.success = true;
      // this.routingAllocator.login();
    }
    else 
    this.error = true;
    this.form.reset();

       
    
  },
  error: (err: Error) => {
    /* istanbul ignore next */
      console.error("profile observer error: " + err);
      this.error = true;
    /* istanbul ignore next */
      this.onReset();},
  
  complete: () => console.log("Response lets you know what happened")
}
}